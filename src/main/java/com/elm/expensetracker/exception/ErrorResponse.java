package com.elm.expensetracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
}
