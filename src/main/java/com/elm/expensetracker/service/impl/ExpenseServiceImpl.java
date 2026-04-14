package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.dto.expense.ExpenseResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.exception.UnauthorizedException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.model.Expense;
import com.elm.expensetracker.model.User;
import com.elm.expensetracker.security.SecurityUtils;
import com.elm.expensetracker.service.interfaces.CategoryService;
import com.elm.expensetracker.service.interfaces.ExpenseService;
import com.elm.expensetracker.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.elm.expensetracker.repository.ExpenseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        Category category = categoryService.findById(request.getCategoryId());

        String currentUsername = SecurityUtils.getCurrentUserName();
        User currentUser = userService.findByUsername(currentUsername);

        Expense newExpense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .category(category)
                .expenseDate(request.getExpenseDate())
                .user(currentUser)
                .build();

        Expense savedExpense = expenseRepository.save(newExpense);
        log.info("Expense created: id={}, title={}, username={}", savedExpense.getId(), savedExpense.getTitle(), currentUsername);
        return ExpenseResponse.from(savedExpense);
    }

    public ExpenseResponse getExpense(Long id) {
        Expense expense = findById(id);
        validateOwnership(expense);
        return ExpenseResponse.from(expense);
    }

    @Override
    @Transactional
    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request) {
        Expense expense = findById(id);
        validateOwnership(expense);

        if (request.getTitle() != null) expense.setTitle(request.getTitle());
        if (request.getDescription() != null) expense.setDescription(request.getDescription());
        if (request.getAmount() != null) expense.setAmount(request.getAmount());
        if (request.getCurrency() != null) expense.setCurrency(request.getCurrency());
        if (request.getCategoryId() != null) expense.setCategory(categoryService.findById(request.getCategoryId()));
        if (request.getExpenseDate() != null) expense.setExpenseDate(request.getExpenseDate());

        return ExpenseResponse.from(expense);
    }

    public void validateOwnership(Expense expense) {
        if (SecurityUtils.isAdmin()) {
            return;
        }

        String currentUsername = SecurityUtils.getCurrentUserName();

        if (!expense.getUser().getUsername().equals(currentUsername)) {
            log.warn("Access denied to expense: expenseId={}, currentUser={}", expense.getId(), currentUsername);
            throw new UnauthorizedException("You don't have permission to access this expense");
        }

    }

    @Override
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = findById(id);
        validateOwnership(expense);
        if(expense.isDeleted()) return;
        expense.markAsDeleted(true);
        log.info("Expense deleted (soft): id={}", id);
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        List<Expense> expenses;
        if (SecurityUtils.isAdmin()) {
            expenses = expenseRepository.findAll();
        } else {
            String currentUsername = SecurityUtils.getCurrentUserName();
            expenses = expenseRepository.findByUser_UsernameAndDeletedFalse(currentUsername);
        }

        return expenses.stream().map(ExpenseResponse::from).toList();
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense with id: " + id + "is not found"));
    }

}
