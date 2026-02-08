package com.elm.expense_tracker_api.controller;

import javax.persistence.Table;
import javax.validation.Valid;

import com.elm.expense_tracker_api.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elm.expense_tracker_api.dto.ExpenseRequest;
import com.elm.expense_tracker_api.dto.ExpenseResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    //Create an Expense
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
         ExpenseResponse response = expenseService.createExpense(request);

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
