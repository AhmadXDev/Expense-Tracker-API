package com.elm.expense_tracker_api.repository;

import com.elm.expense_tracker_api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
