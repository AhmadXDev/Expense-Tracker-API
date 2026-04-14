package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.auth.JwtAuthResponse;
import com.elm.expensetracker.dto.auth.LoginRequest;
import com.elm.expensetracker.dto.user.RegisterRequest;
import com.elm.expensetracker.dto.user.UserResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.model.User;
import com.elm.expensetracker.repository.UserRepository;
import com.elm.expensetracker.security.JwtTokenProvider;
import com.elm.expensetracker.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        // Verify if username is already taken
        if(userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration rejected: username already taken, username={}", request.getUsername());
            throw new IllegalArgumentException(
                    "Username " + request.getUsername() + "is already taken"
            );
        }

        // Create user
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles("USER")
                .enabled(true) //Send email activation in real system
                .build();

        // Save user to database
        User savedUser = userRepository.save(newUser);

        log.info("User registration successfully: username={}, id={},", savedUser.getUsername(), savedUser.getId());

        // Return saved user as response
        return UserResponse.from(savedUser);
    }

    @Override
    public JwtAuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.info("Login successful: username={}", request.getUsername());
        return JwtAuthResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .username(request.getUsername())
                .roles(roles)
                .build();

    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id: " + id + "is not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User by username: " + username + "is not found"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserResponse::from)
                .toList();
    }
}
