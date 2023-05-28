package com.expensetracker.mapper;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;

public interface ExpenseMapper {
    Expense periodToExpense(ExpensePeriod expensePeriod);
    Expense expenseRequestToExpense(ExpenseRequestDTO expenseRequest);
    ExpenseResponseDTO expenseToExpenseResponse(Expense expense);
}
