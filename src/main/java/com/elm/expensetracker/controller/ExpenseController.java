package com.elm.expensetracker.controller;

import javax.validation.Valid;

import com.elm.expensetracker.dto.expense.CreateExpenseRequest;
import com.elm.expensetracker.dto.expense.UpdateExpenseRequest;
import com.elm.expensetracker.service.interfaces.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.elm.expensetracker.dto.expense.ExpenseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    //Create an expense
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody CreateExpenseRequest request) {
         ExpenseResponse response = expenseService.createExpense(request);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Get an expense
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long id) {
        ExpenseResponse response = expenseService.getExpense(id);
        return ResponseEntity.ok(response);
    }

    //Update an expense
    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody UpdateExpenseRequest request) {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }

    //Delete an expense
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    //Get all expense
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> responses = expenseService.getAllExpenses();
        return ResponseEntity.ok(responses);
    }

}
