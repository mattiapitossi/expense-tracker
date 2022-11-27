package com.expensetracker.mapper.impl;

import com.expensetracker.mapper.ExpenseMapper;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public Expense periodToExpense(ExpensePeriod expensePeriod) {
        if(expensePeriod == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setWallet(expensePeriod.getWallet());
        expense.setSubcategory(expensePeriod.getSubcategory());
        expense.setCategory(expensePeriod.getCategory());
        expense.setDescription(expensePeriod.getDescription());
        expense.setLocation(expensePeriod.getLocation());
        expense.setValue(expensePeriod.getValue());
        expense.setTypeOfTransaction(expensePeriod.getTypeOfTransaction());
        expense.setExpenseDate(expensePeriod.getNextPayment());

        return expense;
    }
}
