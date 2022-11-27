package com.expensetracker.mapper;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;

public interface ExpenseMapper {
    Expense periodToExpense(ExpensePeriod expensePeriod);
}
