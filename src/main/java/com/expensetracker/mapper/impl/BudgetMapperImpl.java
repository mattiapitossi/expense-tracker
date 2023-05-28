package com.expensetracker.mapper.impl;

import com.expensetracker.mapper.BudgetMapper;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.dto.request.BudgetDTO;
import com.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BudgetMapperImpl implements BudgetMapper {

    private final CategoryRepository categoryRepository;

    @Override
    public Budget budgetRequestToBudget(BigDecimal expenseValue, LocalDate expenseDate, BudgetDTO budgetDTO) {
        if(budgetDTO == null) {
            return null;
        }

        Budget budget = new Budget();
        budget.setBudgetValue(expenseValue.multiply(BigDecimal.valueOf(budgetDTO.getValue()/100.0)));
        budget.setRemainingValue(expenseValue.multiply(BigDecimal.valueOf(budgetDTO.getValue()/100.0)));
        budget.setMonth(expenseDate.getMonthValue());
        budget.setYear(expenseDate.getYear());

        Category category = categoryRepository.findByName(budgetDTO.getCategory());
        budget.setCategory(category);

        return budget;
    }

    @Override
    public Budget budgetToBudget(Budget budget, int month, int year) {
        Budget newBudget = new Budget();
        newBudget.setBudgetValue(budget.getRemainingValue());
        newBudget.setRemainingValue(budget.getRemainingValue());
        newBudget.setMonth(month);
        newBudget.setYear(year);
        newBudget.setCategory(budget.getCategory());

        return newBudget;
    }
}
