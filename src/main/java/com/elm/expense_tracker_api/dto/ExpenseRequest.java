package com.elm.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.elm.expense_tracker_api.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpenseRequest {

    private String title; 
    private String description; 
    private BigDecimal amount; 
    private String currency; 
    private Category category;
    private LocalDate expenseDate;
}
