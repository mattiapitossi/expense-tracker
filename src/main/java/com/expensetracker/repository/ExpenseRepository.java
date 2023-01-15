package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    boolean existsByCategory_Id(Long id);

    boolean existsByWallet_Id(Long id);

    List<Expense> getExpenseByWallet(Wallet wallet);

    List<Expense> getExpenseByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
}
