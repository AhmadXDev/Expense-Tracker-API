package com.elm.expensetracker.controller;

import com.elm.expensetracker.dto.category.CategoryRequest;
import com.elm.expensetracker.dto.category.CategoryResponse;
import com.elm.expensetracker.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;

    // Create a category
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse response = categoryServiceImpl.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get a category
    @GetMapping
    public ResponseEntity<CategoryResponse> getCategory(Long id) {
        CategoryResponse response = categoryServiceImpl.getCategory(id);
    }



}
