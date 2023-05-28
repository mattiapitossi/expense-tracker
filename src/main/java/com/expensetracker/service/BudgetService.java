package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetMovement;
import com.expensetracker.model.Expense;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.BudgetResponseDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    List<Budget> getAllBudgets();

    void createOrUpdateBudget(ExpenseRequestDTO expenseRequestDTO);

    void createBudgetFromBudget(Budget budget, int month, int year);

    void updateBudget(ExpenseResponseDTO expenseResponseDTO, Expense expense);

    List<BudgetResponseDTO> getAllBudgetsDTOBy(int month, int year);

    List<Budget> getAllBudgetsBy(int month, int year);

    void modifyBudget(BudgetMovement budgetMovement);

    Budget getBudgetById(Long id);

    Optional<Budget> getBudgetBy(String categoryName, int month, int year);

    void updateBudget(ExpenseResponseDTO expenseResponseDTO, BudgetMovement budgetMovement, Expense newExpense);
}
