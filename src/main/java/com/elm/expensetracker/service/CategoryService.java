package com.elm.expensetracker.service;

import com.elm.expensetracker.dto.request.CategoryRequest;
import com.elm.expensetracker.dto.response.CategoryResponse;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category(
                null,
                request.getName(),
                request.getDescription()
        );

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }


}
