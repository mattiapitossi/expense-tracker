package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;
import com.expensetracker.model.dto.response.CategoryExpensesDTO;
import com.expensetracker.model.dto.response.SubcategoryExpensesDTO;
import com.expensetracker.model.dto.response.YearExpensesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    boolean existsByCategoryId(Long id);

    boolean existsByWalletId(Long id);

    List<Expense> getExpenseByWallet(Wallet wallet);

    @Query("select e from Expense e where e.expenseDate between ?1 and ?2 order by e.expenseDate DESC")
    List<Expense> getExpenseByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("select new com.expensetracker.model.dto.response.CategoryExpensesDTO(e.category, SUM(e.value)) " +
            "from Expense e " +
            "where e.typeOfTransaction = 'OUT' AND e.expenseDate between ?1 and ?2 GROUP BY e.category")
    List<CategoryExpensesDTO> getCategoryExpensesBy(LocalDate startDate, LocalDate endDate);

    @Query("select new com.expensetracker.model.dto.response.SubcategoryExpensesDTO(e.subcategory, SUM(CASE WHEN e.typeOfTransaction = 'IN' THEN e.value ELSE - e.value END ), MONTH(e.expenseDate)) " +
            "from Expense e " +
            "WHERE e.expenseDate between ?1 and ?2 GROUP BY e.subcategory, MONTH(e.expenseDate)")
    List<SubcategoryExpensesDTO> getSubcategoryExpensesBy(LocalDate startDate, LocalDate endDate);

    @Query("Select new com.expensetracker.model.dto.response.YearExpensesDTO(MONTH(e.expenseDate), SUM(e.value), e.typeOfTransaction) " +
            "FROM Expense e " +
            "WHERE e.expenseDate between ?1 and ?2 " +
            "GROUP BY MONTH(e.expenseDate), e.typeOfTransaction ORDER BY MONTH(e.expenseDate) ASC")
    List<YearExpensesDTO> getExpensesBy(LocalDate startDate, LocalDate endDate);
}
