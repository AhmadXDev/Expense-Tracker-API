package com.elm.expense_tracker_api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpenseRequest {

    private String title; 
    private String description; 
    private BigDecimal amount; 
    private String currency; 
    private Long categoryId;
    private LocalDate expenseDate;
}
