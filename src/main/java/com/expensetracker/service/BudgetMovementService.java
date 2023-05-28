package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;

public interface BudgetMovementService {
    void createInBudgetMovement(Expense expense, ExpenseRequestDTO expenseRequestDTO);

    void createOutBudgetMovement(Expense expense);

    void modifyBudgets(Expense oldExpense);

    void duplicateBudgetsMovements(ExpenseResponseDTO expenseResponseDTO, Expense oldExpense, Expense newExpense);
}
