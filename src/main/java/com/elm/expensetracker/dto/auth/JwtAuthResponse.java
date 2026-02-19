package com.elm.expensetracker.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType;
    private String username;
    private String roles;
}
