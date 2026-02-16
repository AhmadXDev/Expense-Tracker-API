package com.elm.expensetracker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Getter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private final String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be 6 characters or higher")
    private final String password;

    @Email(message = "Email must be a valid email")
    @NotBlank(message = "Email is required")
    private String email;


}
