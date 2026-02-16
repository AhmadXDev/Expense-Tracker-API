package com.elm.expensetracker.service.impl;

import com.elm.expensetracker.dto.category.CreateCategoryRequest;
import com.elm.expensetracker.dto.category.CategoryResponse;
import com.elm.expensetracker.dto.category.UpdateCategoryRequest;
import com.elm.expensetracker.model.Category;
import com.elm.expensetracker.repository.CategoryRepository;
import com.elm.expensetracker.service.interfaces.CategoryService;
import com.elm.expensetracker.service.base.BaseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends BaseEntityService<Category, CategoryRepository> implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }

    @Override
    public CategoryResponse getCategory(Long id) {
        Category category = findById(id);
        return CategoryResponse.from(category);

    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = findById(id);
        if (request.getName() != null) category.setName(request.getName());
        if(request.getDescription() != null) category.setDescription(request.getDescription());

        return CategoryResponse.from(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResponse::from).toList();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = findById(id);
        if(category.isDeleted()) return;
        category.markAsDeleted(true);
    }

    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }

    @Override
    protected String getEntityName() {
        return "Category";
    }
}
