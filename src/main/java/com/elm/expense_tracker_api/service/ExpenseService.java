package com.elm.expense_tracker_api.service;

import com.elm.expense_tracker_api.dto.ExpenseRequest;
import com.elm.expense_tracker_api.dto.ExpenseResponse;
import com.elm.expense_tracker_api.model.Expense;
import com.elm.expense_tracker_api.repository.CategoryRepository;
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
                request.getCategory(),
                request.getExpenseDate()
        );

        expenseRepository.save(expense);
        return ExpenseResponse.from(expense);
    }
}
