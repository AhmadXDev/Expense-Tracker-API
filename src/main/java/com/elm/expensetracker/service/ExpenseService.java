package com.elm.expensetracker.service;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.ExpenseResponse;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.model.Expense;

import java.util.List;

public interface ExpenseService {
    ExpenseResponse createExpense(CreateExpenseRequest request);
    ExpenseResponse getExpense(Long id);
    ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request);
    void deleteExpense(Long id);
    List<ExpenseResponse> getAllExpenses();

}
