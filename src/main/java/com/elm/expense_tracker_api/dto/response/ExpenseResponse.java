package com.elm.expense_tracker_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.elm.expense_tracker_api.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseResponse {

    private final String title; 
    private final BigDecimal amount; 
    private final String currency; 
    private final Long categoryId;
    private final LocalDate expenseDate;

    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getTitle(),
                expense.getAmount(),
                expense.getCurrency(),
                expense.
                expense.getExpenseDate()
        );
    }


}
