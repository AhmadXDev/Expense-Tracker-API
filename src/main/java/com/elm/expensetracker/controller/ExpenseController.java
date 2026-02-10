package com.elm.expensetracker.controller;

import javax.validation.Valid;

import com.elm.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.elm.expensetracker.dto.request.ExpenseRequest;
import com.elm.expensetracker.dto.response.ExpenseResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    //Create an Expense
    @PostMapping("/create")
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
         ExpenseResponse response = expenseService.createExpense(request);

         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
