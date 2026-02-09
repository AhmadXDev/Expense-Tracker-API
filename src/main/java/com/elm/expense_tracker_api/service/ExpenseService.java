package com.elm.expense_tracker_api.service;

import com.elm.expense_tracker_api.dto.request.ExpenseRequest;
import com.elm.expense_tracker_api.dto.response.ExpenseResponse;
import com.elm.expense_tracker_api.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elm.expense_tracker_api.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseResponse createExpense(ExpenseRequest request) {
        Expense expense = new Expense(
                null,
                request.getTitle(),
                request.getDescription(),
                request.getAmount(),
                request.getCurrency(),
                request.getCategoryId(),
                request.getExpenseDate()
        );

        expenseRepository.save(expense);
        return ExpenseResponse.from(expense);
    }
}
