package com.elm.expensetracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:MySecretKeyForJWTTokenGenerationMustBeLongEnough12345}")
    private String jwtSecret;

    // 86400000 milliseconds is 24 hours
    @Value("${jwt.expiration:86400000}")
    private Long jwtExpiration;
}
