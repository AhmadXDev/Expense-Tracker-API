package com.elm.expensetracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.elm.expensetracker.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final BigDecimal amount; 
    private final String currency; 
    private final Long categoryId;
    private final String categoryName;
    private final LocalDate expenseDate;

    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getTitle(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCurrency(),
                expense.getCategory().getId(),
                expense.getCategory().getName(),
                expense.getExpenseDate()
        );
    }


}
