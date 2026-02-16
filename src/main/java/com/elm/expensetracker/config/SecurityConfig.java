package com.elm.expensetracker.config;
import com.elm.expensetracker.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("Ahmed")
//                .password(passwordEncoder().encode("password123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("Admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN", "USER")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // ================== PUBLIC ENDPOINTS ==================
                        .antMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // ================== CATEGORY ENDPOINTS ==================
                        .antMatchers(HttpMethod.GET, "/category/**").authenticated()
                        .antMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PATCH, "/category/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")

                        // ================== EXPENSE ENDPOINTS ==================
                        .antMatchers("/expense").authenticated()

                        // ================= ALL OTHER ENDPOINTS ==================
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
