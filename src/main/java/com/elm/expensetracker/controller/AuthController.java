package com.elm.expensetracker.controller;
import com.elm.expensetracker.dto.auth.JwtAuthResponse;
import com.elm.expensetracker.dto.auth.LoginRequest;
import com.elm.expensetracker.dto.user.RegisterRequest;
import com.elm.expensetracker.dto.user.UserResponse;
import com.elm.expensetracker.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest request) {
       log.debug("Login attempt: username={}", request.getUsername());
       JwtAuthResponse response = userService.login(request);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.debug("Registration attempt: username={}", request.getUsername());
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




}
