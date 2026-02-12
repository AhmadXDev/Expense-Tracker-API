package com.elm.expensetracker.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UpdateExpenseRequest {

    @Size(max = 100, message = "Title can't exceed 100 characters")
    private final String title;

    @Size(max = 500, message = "Description must no exceed 500 characters")
    private final String description;

    @Positive(message = "Amount must be positive")
    @Digits(integer = 17, fraction = 2, message = "Amount must have maximum of 17 digits and 2 decimal places")
    private BigDecimal amount;

    @Pattern(regexp = "[A-Z]{3}", message = "currency must be 3 characters and between A to Z")
    private String currency;

    private Long categoryId;

    @PastOrPresent(message = "Expense date must not be in the future")
    private LocalDate expenseDate;


}
