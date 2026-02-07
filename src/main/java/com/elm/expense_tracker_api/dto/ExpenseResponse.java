package com.elm.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseResponse {

    private final String title; 
    private final BigDecimal amount; 
    private final String currency; 
    private final Category category; 
    private final LocalDate expenseDate; 
}
