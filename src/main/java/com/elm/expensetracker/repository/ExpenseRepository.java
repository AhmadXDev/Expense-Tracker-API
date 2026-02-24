package com.elm.expensetracker.repository;

import com.elm.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDeletedFalse();

    Optional<Expense> findByIdAndDeletedFalse(Long id);

    List<Expense> findByUser_UsernameAndDeletedFalse(String username);

    // return all expenses by category
    List<Expense> findByCategory_Id(Long categoryId);

    // return one expense by Date
    List<Expense> findByExpenseDate(LocalDate expenseDate);

    // return expense larger by 100
    List<Expense> findByAmountGreaterThan(BigDecimal amountIsGreaterThan);

}
