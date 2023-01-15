package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import com.expensetracker.model.YearExpensesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    boolean existsByCategory_Id(Integer id);

    boolean existsByWallet_Id(Integer id);

    List<Expense> getExpenseByWallet(Wallet wallet);

    @Query("select e from Expense e where e.expenseDate between ?1 and ?2")
    List<Expense> getExpenseByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("Select new com.expensetracker.model.YearExpensesDTO(function('date_format', e.expenseDate, '%M'), SUM(e.value), e.typeOfTransaction) " +
            "FROM Expense e " +
            "WHERE e.expenseDate between ?1 and ?2 " +
            "GROUP BY function('date_format', e.expenseDate, '%M'), e.typeOfTransaction ORDER BY function('date_format', e.expenseDate, '%M') DESC ")
    List<YearExpensesDTO> getExpensesByYear(LocalDate startDate, LocalDate endDate);
}
