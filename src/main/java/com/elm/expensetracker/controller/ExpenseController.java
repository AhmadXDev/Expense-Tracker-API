package com.elm.expensetracker.controller;

import javax.validation.Valid;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.service.impl.ExpenseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.elm.expensetracker.dto.expense.ExpenseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseServiceImpl expenseServiceImpl;

    // Create an expense
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody CreateExpenseRequest request) {
         ExpenseResponse response = expenseServiceImpl.createExpense(request);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get an expense
    @GetMapping("{id}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long id) {
        ExpenseResponse response = expenseServiceImpl.getExpense(id);
        return ResponseEntity.ok(response);
    }

    // Update an expense
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody UpdateExpenseRequest request) {
        ExpenseResponse response = expenseServiceImpl.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }

    // Delete an expense
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseServiceImpl.deleteExpense(id);
    }






}
