package com.elm.expensetracker.service.interfaces;

import com.elm.expensetracker.dto.category.CategoryResponse;
import com.elm.expensetracker.dto.category.CreateCategoryRequest;
import com.elm.expensetracker.dto.category.UpdateCategoryRequest;
import com.elm.expensetracker.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse getCategory(Long id);
    CategoryResponse updateCategory(Long id, UpdateCategoryRequest request);
    List<CategoryResponse> getCategories();
    void deleteCategory(Long id);
    Category findById(Long id);
}
