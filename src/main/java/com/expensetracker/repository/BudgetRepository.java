package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import com.expensetracker.model.dto.response.BudgetResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByCategoryNameAndMonthAndYear(String categoryName, Integer month, Integer year);

    boolean existsByCategoryNameAndMonthAndYear(String categoryName, Integer month, Integer year);

    @Query("Select new com.expensetracker.model.dto.response.BudgetResponseDTO(b.category.name, b.budgetValue, b.remainingValue, b.month, b.year) " +
            "FROM Budget b " +
            "WHERE b.month = ?1 AND b.year = ?2 " +
            "ORDER BY b.month ASC")
    List<BudgetResponseDTO> getBudgetsBy(Integer month, Integer year);

    List<Budget> findBudgetsByMonthAndYear(Integer month, Integer year);
}
