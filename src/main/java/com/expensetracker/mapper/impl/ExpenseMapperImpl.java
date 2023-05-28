package com.expensetracker.mapper.impl;

import com.expensetracker.mapper.ExpenseMapper;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.model.dto.response.ExpenseResponseDTO;
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

    @Override
    public Expense expenseRequestToExpense(ExpenseRequestDTO expenseRequest) {
        if(expenseRequest == null) {
            return null;
        }

        Expense expense = new Expense();
        mapper(expense, expenseRequest);

        return expense;
    }

    @Override
    public ExpenseResponseDTO expenseToExpenseResponse(Expense expense) {
        if(expense == null) {
            return null;
        }

        ExpenseResponseDTO expenseResponse = new ExpenseResponseDTO();
        mapper(expenseResponse, expense);

        return expenseResponse;
    }

    private void mapper(Expense expenseSet, Expense expenseGet) {
        expenseSet.setWallet(expenseGet.getWallet());
        expenseSet.setSubcategory(expenseGet.getSubcategory());
        expenseSet.setCategory(expenseGet.getCategory());
        expenseSet.setDescription(expenseGet.getDescription());
        expenseSet.setLocation(expenseGet.getLocation());
        expenseSet.setValue(expenseGet.getValue());
        expenseSet.setTypeOfTransaction(expenseGet.getTypeOfTransaction());
        expenseSet.setExpenseDate(expenseGet.getExpenseDate());
    }
}
