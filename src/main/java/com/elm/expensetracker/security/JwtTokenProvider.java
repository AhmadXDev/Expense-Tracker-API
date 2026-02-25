package com.elm.expensetracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret:MySecretKeyForJWTTokenGenerationMustBeLongEnough12345}")
    private String jwtSecret;

    // 86400000 milliseconds is 24 hours
    @Value("${jwt.expiration:86400000}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRolesFromToken(String token) {
        return getClaims(token).get("roles", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (MalformedJwtException ex) {
            // Token structure is invalid
            log.debug("Invalid JWT token (malformed): {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            // Token is expired
            log.debug("Invalid JWT token (expired): {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            // Token type is not supported
            log.debug("Invalid JWT token (unsupported): {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            // Token string is empty or null
            log.debug("Invalid JWT token (empty/null): {}", ex.getMessage());
        } catch (SignatureException ex) {
            // Signature does not match, token was tampered
            log.debug("Invalid JWT token (token tampered?): {}", ex.getMessage());
        }

        return false;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
