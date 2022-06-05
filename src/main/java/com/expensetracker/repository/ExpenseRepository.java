package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    public boolean existsByCategory_Id(Integer id);

    public boolean existsByWallet_Id(Integer id);

    List<Expense> getExpenseByWallet(Wallet wallet);
}
