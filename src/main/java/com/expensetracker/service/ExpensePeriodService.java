package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;

import java.util.List;

public interface ExpensePeriodService {
    ExpensePeriod createNewExpensePeriod(ExpensePeriod expensePeriod);

    void createExpenses(ExpensePeriod expensePeriod);

    List<ExpensePeriod> getAllExpensesPeriod();

    ExpensePeriod modifyExpensePeriod(ExpensePeriod expensePeriod);

    ExpensePeriod duplicateExpensePeriod(Integer expenseId);

    void deleteExpensePeriodById(Integer expenseId);
}
