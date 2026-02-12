package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.category.CategoryRequest;
import com.elm.expensetracker.dto.category.CategoryResponse;
import com.elm.expensetracker.exception.ResourceNotFoundException;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }

    public CategoryResponse getCategory(Long id) {
        Category category = findCategoryById(id);
        return CategoryResponse.from(category);

    }

    public  Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }


}
