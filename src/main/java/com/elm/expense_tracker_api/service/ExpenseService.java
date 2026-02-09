package com.elm.expense_tracker_api.service;

import com.elm.expense_tracker_api.dto.request.ExpenseRequest;
import com.elm.expense_tracker_api.dto.response.ExpenseResponse;
import com.elm.expense_tracker_api.model.Category;
import com.elm.expense_tracker_api.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elm.expense_tracker_api.repository.CategoryRepository;
import com.elm.expense_tracker_api.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseResponse createExpense(ExpenseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Category not found with id: " + request.getCategoryId()
            ));

        Expense expense = new Expense(
                null,
                request.getTitle(),
                request.getDescription(),
                request.getAmount(),
                request.getCurrency(),
                category,
                request.getExpenseDate()
        );

        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseResponse.from(savedExpense);
    }
}
