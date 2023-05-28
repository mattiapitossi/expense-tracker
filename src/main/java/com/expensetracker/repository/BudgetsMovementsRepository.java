package com.expensetracker.repository;

import com.expensetracker.model.BudgetMovement;
import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetsMovementsRepository extends JpaRepository<BudgetMovement, Long> {

    List<BudgetMovement> findBudgetMovementsByExpense(Expense expense);
}
