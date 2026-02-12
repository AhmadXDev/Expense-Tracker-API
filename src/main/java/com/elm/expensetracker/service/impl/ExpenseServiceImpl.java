package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.dto.expense.ExpenseResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.CategoryRepository;
import com.elm.expensetracker.repository.ExpenseRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        Category category = findCategoryById(request.getCategoryId());

        Expense newExpense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .category(category)
                .expenseDate(request.getExpenseDate())
                .build();

        Expense savedExpense = expenseRepository.save(newExpense);
        return ExpenseResponse.from(savedExpense);
    }

    public ExpenseResponse getExpense(Long id) {
        Expense expense = findExpenseById(id);
        return ExpenseResponse.from(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request) {
        Expense expense = findExpenseById(id);

        if (request.getTitle() != null) expense.setTitle(request.getTitle());
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getCurrency() != null) expense.setCurrency(request.getCurrency());
        if (request.getCategoryId() != null) expense.setCategory(findCategoryById(id));
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());

        return ExpenseResponse.from(expense);
    }

    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = findExpenseById(id);

        if(expense.isDeleted()) return;

        expense.markAsDeleted(true);
    }

    public Expense findExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }

}
