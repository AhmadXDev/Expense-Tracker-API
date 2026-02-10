package com.elm.expensetracker.service;

import com.elm.expensetracker.dto.request.ExpenseRequest;
import com.elm.expensetracker.dto.response.ExpenseResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.CategoryRepository;
import com.elm.expensetracker.repository.ExpenseRepository;

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
