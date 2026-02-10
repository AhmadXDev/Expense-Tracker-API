package com.elm.expensetracker.repository;

import com.elm.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // return all expenses by category
    // return one expende by Date
    // return expense larger by 100
}
