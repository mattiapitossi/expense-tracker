package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.model.Wallet;

import java.util.List;

public interface ExpenseService {
    boolean expenseWithCategoryExist(Long categoryId);

    boolean expenseWithWalletExist(Long walletId);

    List<Expense> expensesLinkedToWallet(Wallet wallet);

    Expense duplicateExpense(Long expenseId);

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesPeriodByDate(int month, int year);

    void deleteExpenseById(Long expenseId);

    Expense createNewExpense(Expense expense);

    Expense modifyExpense(Expense expense);

    void saveExpenseFromPeriod(ExpensePeriod expensePeriod);
}
