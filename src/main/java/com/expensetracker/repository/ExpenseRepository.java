package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    boolean existsByCategory_Id(Integer id);

    boolean existsByWallet_Id(Integer id);

    List<Expense> getExpenseByWallet(Wallet wallet);

    List<Expense> getExpenseByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
}
