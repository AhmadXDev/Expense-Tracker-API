package com.elm.expense_tracker_api.controller;

import javax.persistence.Table;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elm.expense_tracker_api.dto.ExpenseResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Expenses")
@Tag(name = "Expenses", description = "Expenses Managment API")
public class ExpenseController {


    @PostMapping
    public ResponseEntity<ExpenseResponse> () {
        //TODO: process POST request
        
        return entity;
    }
    

}
