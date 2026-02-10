package com.elm.expensetracker.repository;

import com.elm.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
