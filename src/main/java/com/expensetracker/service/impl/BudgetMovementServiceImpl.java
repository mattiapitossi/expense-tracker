package com.expensetracker.service.impl;

import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetMovement;
import com.expensetracker.model.Expense;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;
import com.expensetracker.repository.BudgetsMovementsRepository;
import com.expensetracker.service.BudgetMovementService;
import com.expensetracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetMovementServiceImpl implements BudgetMovementService {

    private final BudgetsMovementsRepository budgetsMovementsRepository;

    private final BudgetService budgetService;

    @Override
    public void createInBudgetMovement(Expense expense, ExpenseRequestDTO expenseRequestDTO) {
        if (expenseRequestDTO.getBudgets() != null) {
            expenseRequestDTO.getBudgets().forEach(budgetDTO -> {
                        Optional<Budget> budget = budgetService.getBudgetBy(budgetDTO.getCategory(), expense.getExpenseDate().getMonthValue(), expense.getExpenseDate().getYear());
                        budget.ifPresent(budgetGet -> {
                            BudgetMovement budgetMovement = new BudgetMovement();
                            budgetMovement.setBudget(budgetGet);
                            budgetMovement.setExpense(expense);
                            budgetMovement.setValue(expenseRequestDTO.getValue().multiply(BigDecimal.valueOf(budgetDTO.getValue() / 100.0)));
                            budgetsMovementsRepository.save(budgetMovement);
                        });
                    }
            );
        }
    }

    @Override
    public void createOutBudgetMovement(Expense expense) {
        Optional<Budget> budget = budgetService.getBudgetBy(expense.getCategory().getName(), expense.getExpenseDate().getMonthValue(), expense.getExpenseDate().getYear());
        budget.ifPresent(budgetGet -> {
            BudgetMovement budgetMovement = new BudgetMovement();
            budgetMovement.setBudget(budgetGet);
            budgetMovement.setExpense(expense);
            budgetMovement.setValue(expense.getValue());
            budgetsMovementsRepository.save(budgetMovement);
        });
    }

    @Override
    public void modifyBudgets(Expense oldExpense) {
        List<BudgetMovement> budgetMovementList = budgetsMovementsRepository.findBudgetMovementsByExpense(oldExpense);
        if(budgetMovementList != null) {
            budgetMovementList.forEach(budgetMovement -> {
                budgetService.modifyBudget(budgetMovement);
                budgetsMovementsRepository.delete(budgetMovement);
            });
        }
    }

    @Override
    public void duplicateBudgetsMovements(ExpenseResponseDTO expenseResponseDTO, Expense oldExpense, Expense newExpense) {
        List<BudgetMovement> budgetMovementList = budgetsMovementsRepository.findBudgetMovementsByExpense(oldExpense);
        if(budgetMovementList != null) {
            budgetMovementList.forEach(budgetMovement -> {
                BudgetMovement newBudgetMovement = new BudgetMovement();
                newBudgetMovement.setBudget(budgetMovement.getBudget());
                newBudgetMovement.setExpense(newExpense);
                newBudgetMovement.setValue(budgetMovement.getValue());

                budgetsMovementsRepository.save(newBudgetMovement);
                if("IN".equalsIgnoreCase(newExpense.getTypeOfTransaction())) {
                    budgetService.updateBudget(expenseResponseDTO, budgetMovement, newExpense);
                } else {
                    budgetService.updateBudget(expenseResponseDTO, newExpense);
                }
            });
        }

    }
}
