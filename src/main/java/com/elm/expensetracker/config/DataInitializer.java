package com.elm.expensetracker.config;

import com.elm.expensetracker.model.User;
import com.elm.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Override
    public void run(String... args) throws Exception {
        
        // Check wither admin is needed to be created
        if (adminUsername != null && adminPassword != null && adminEmail != null) {

            if (userRepository.count() == 0) {

                // Create admin
                User admin = User.builder()
                        .username(adminUsername)
                        .password(passwordEncoder.encode(adminPassword))
                        .email(adminEmail)
                        .roles("ADMIN,USER")
                        .enabled(true)
                        .build();

                // Save admin to the database
                userRepository.save(admin);


                System.out.println("Admin account created and saved to the database");
            }
            else {
                System.out.println("Database already has users. Skipping admin creation");
            }
        }
        else {
            System.out.println("Environment variables are not set");
        }

    }
}
