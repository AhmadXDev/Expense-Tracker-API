package com.elm.expensetracker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpenseRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title; 

    @Size(max = 500, message = "Descriptoin must not exceed 500 characters")
    private String description; 

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Digits(integer = 17, fraction = 2, message = "Amount must have maximum of 17 digits and 2 decimal places ")
    private BigDecimal amount; 

    @Pattern(regexp = "[A-Z]{3}")
    @NotBlank(message = "Currency is required")
    private String currency; 

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in the future")
    private LocalDate expenseDate;
}
