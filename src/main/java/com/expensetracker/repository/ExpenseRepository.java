package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import com.expensetracker.model.dto.CategoryExpensesDTO;
import com.expensetracker.model.dto.YearExpensesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    boolean existsByCategory_Id(Long id);

    boolean existsByWallet_Id(Long id);

    List<Expense> getExpenseByWallet(Wallet wallet);

    @Query("select e from Expense e where e.expenseDate between ?1 and ?2 order by e.expenseDate DESC")
    List<Expense> getExpenseByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("select new com.expensetracker.model.dto.CategoryExpensesDTO(e.category, SUM(e.value)) " +
            "from Expense e " +
            "where e.typeOfTransaction = 'OUT' AND e.expenseDate between ?1 and ?2 GROUP BY e.category")
    List<CategoryExpensesDTO> getCategoryExpensesByMonth(LocalDate startDate, LocalDate endDate);

    @Query("Select new com.expensetracker.model.dto.YearExpensesDTO(MONTH(e.expenseDate), SUM(e.value), e.typeOfTransaction) " +
            "FROM Expense e " +
            "WHERE e.expenseDate between ?1 and ?2 " +
            "GROUP BY MONTH(e.expenseDate), e.typeOfTransaction ORDER BY MONTH(e.expenseDate) ASC")
    List<YearExpensesDTO> getExpensesByYear(LocalDate startDate, LocalDate endDate);
}
