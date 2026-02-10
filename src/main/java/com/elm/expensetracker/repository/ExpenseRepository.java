package com.elm.expensetracker.repository;

import com.elm.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // return all expenses by category
    List<Expense> findByCategory_Id(Long categoryId);

    // return one expende by Date
    Expense findByExpenseDate(LocalDate expenseDate);

    // return expense larger by 100
    Expense findByAmountGreaterThan(BigDecimal amountIsGreaterThan);
}
