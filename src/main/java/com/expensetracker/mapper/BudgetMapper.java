package com.expensetracker.mapper;

import com.expensetracker.model.Budget;
import com.expensetracker.model.dto.request.BudgetDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BudgetMapper {

    Budget budgetRequestToBudget(BigDecimal expenseValue, LocalDate expenseDate, BudgetDTO budgetDTO);

    Budget budgetToBudget(Budget budget, int month, int year);
}
