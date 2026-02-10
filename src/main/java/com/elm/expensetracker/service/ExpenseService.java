package com.elm.expensetracker.service;

import com.elm.expensetracker.dto.request.ExpenseRequest;
import com.elm.expensetracker.dto.response.ExpenseResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.CategoryRepository;
import com.elm.expensetracker.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseResponse createExpense(ExpenseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Category not found with id: " + request.getCategoryId()
            ));

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .category(category)
                .expenseDate(request.getExpenseDate())
                .build();

        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseResponse.from(savedExpense);
    }
}
