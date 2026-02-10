package com.elm.expensetracker.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
