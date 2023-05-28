package com.expensetracker.service.impl;

import com.expensetracker.mapper.BudgetMapper;
import com.expensetracker.model.Budget;
import com.expensetracker.model.BudgetMovement;
import com.expensetracker.model.Expense;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.BudgetResponseDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public void createOrUpdateBudget(ExpenseRequestDTO expenseRequestDTO) {
        if(expenseRequestDTO.getBudgets() != null) {
            expenseRequestDTO.getBudgets().forEach(budgetDTO -> {
                Optional<Budget> optionalBudget = budgetRepository.findByCategoryNameAndMonthAndYear(budgetDTO.getCategory(), expenseRequestDTO.getExpenseDate().getMonthValue(), expenseRequestDTO.getExpenseDate().getYear());
                optionalBudget.ifPresentOrElse(
                        budget -> {
                            BigDecimal expenseToAdd = expenseRequestDTO.getValue().multiply(BigDecimal.valueOf(budgetDTO.getValue() / 100.0));
                            budget.setBudgetValue(budget.getBudgetValue().add(expenseToAdd));
                            budget.setRemainingValue(budget.getRemainingValue().add(expenseToAdd));
                            budgetRepository.save(budget);
                        },
                        () -> {
                            Budget budget = budgetMapper.budgetRequestToBudget(expenseRequestDTO.getValue(), expenseRequestDTO.getExpenseDate(), budgetDTO);
                            budgetRepository.save(budget);
                        });
            });
        }
    }

    @Override
    public void createBudgetFromBudget(Budget budget, int month, int year) {
        if(!budgetRepository.existsByCategoryNameAndMonthAndYear(budget.getCategory().getName(), month, year)) {
            Budget newBudget = budgetMapper.budgetToBudget(budget, month, year);
            newBudget.setMonth(month);

            budgetRepository.save(newBudget);
        }
    }

    @Override
    public void updateBudget(ExpenseResponseDTO expenseResponseDTO, Expense expense) {
        Optional<Budget> optionalBudget = budgetRepository.findByCategoryNameAndMonthAndYear(expense.getCategory().getName(), expense.getExpenseDate().getMonthValue(), expense.getExpenseDate().getYear());
        optionalBudget.ifPresent(budget -> {
            budget.setRemainingValue(budget.getRemainingValue().subtract(expense.getValue()));
            if(budget.getRemainingValue().compareTo(BigDecimal.ZERO) <= 0) {
                expenseResponseDTO.setMessage("The budget limit for the category " + expense.getCategory().getName() + " has been exceeded by " + budget.getRemainingValue().abs() + "€");
            }
            budgetRepository.save(budget);
        });
    }

    @Override
    public List<BudgetResponseDTO> getAllBudgetsDTOBy(int month, int year) {
        return budgetRepository.getBudgetsBy(month, year);
    }

    @Override
    public List<Budget> getAllBudgetsBy(int month, int year) {
        return budgetRepository.findBudgetsByMonthAndYear(month, year);
    }

    @Override
    public void modifyBudget(BudgetMovement budgetMovement) {
        Budget budget = budgetMovement.getBudget();

        if("IN".equalsIgnoreCase(budgetMovement.getExpense().getTypeOfTransaction())) {
            budget.setBudgetValue(budget.getBudgetValue().subtract(budgetMovement.getValue()));
            budget.setRemainingValue(budget.getRemainingValue().subtract(budgetMovement.getValue()));
        } else if("OUT".equalsIgnoreCase(budgetMovement.getExpense().getTypeOfTransaction())) {
            budget.setRemainingValue(budget.getRemainingValue().add(budgetMovement.getValue()));
        }

        budgetRepository.save(budget);
    }

    @Override
    public Budget getBudgetById(Long id) {
        return budgetRepository.getReferenceById(id);
    }

    @Override
    public Optional<Budget> getBudgetBy(String categoryName, int month, int year) {
        return budgetRepository.findByCategoryNameAndMonthAndYear(categoryName, month, year);
    }

    @Override
    public void updateBudget(ExpenseResponseDTO expenseResponseDTO, BudgetMovement budgetMovement, Expense newExpense) {
        Budget budget = budgetMovement.getBudget();

        if("IN".equalsIgnoreCase(newExpense.getTypeOfTransaction())) {
            budget.setBudgetValue(budget.getBudgetValue().add(budgetMovement.getValue()));
            budget.setRemainingValue(budget.getRemainingValue().add(budgetMovement.getValue()));
        } else if("OUT".equalsIgnoreCase(newExpense.getTypeOfTransaction())) {
            budget.setRemainingValue(budget.getRemainingValue().subtract(budgetMovement.getValue()));
            if(budget.getRemainingValue().compareTo(BigDecimal.ZERO) <= 0) {
                expenseResponseDTO.setMessage("The budget limit for the category " + newExpense.getCategory().getName() + " has been exceeded by " + budget.getRemainingValue().abs() + "€");
            }
        }

        budgetRepository.save(budget);
    }
}
