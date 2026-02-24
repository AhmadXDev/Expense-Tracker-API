package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.dto.expense.ExpenseResponse;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import com.elm.expensetracker.service.interfaces.CategoryService;
import com.elm.expensetracker.service.interfaces.ExpenseService;
import com.elm.expensetracker.service.base.BaseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.ExpenseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl extends BaseEntityService<Expense, ExpenseRepository> implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        Category category = categoryService.findById(request.getCategoryId());

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
        Expense expense = findById(id);
        return ExpenseResponse.from(expense);
    }

    @Override
    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request) {
        Expense expense = findById(id);

        if (request.getTitle() != null) expense.setTitle(request.getTitle());
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getCurrency() != null) expense.setCurrency(request.getCurrency());
        if (request.getCategoryId() != null) expense.setCategory(categoryService.findById(request.getCategoryId()));
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());

        return ExpenseResponse.from(expense);
    }

    @Override
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = findById(id);
        if(expense.isDeleted()) return;
        expense.markAsDeleted(true);
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(ExpenseResponse::from).toList();
    }

    @Override
    protected ExpenseRepository getRepository() {
        return expenseRepository;
    }

    @Override
    protected String getEntityName() {
        return "Expense";
    }
}
