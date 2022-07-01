package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Wallet;

import java.util.List;

public interface ExpenseService {
    boolean expenseWithCategoryExist(Integer categoryId);

    boolean expenseWithWalletExist(Integer walletId);

    List<Expense> expensesLinkedToWallet(Wallet wallet);

    Expense duplicateExpense(Integer expenseId);

    List<Expense> getAllExpenses();

    void deleteExpenseById(Integer expenseId);

    Expense createNewExpense(Expense expense);

    Expense modifyExpense(Expense expense);
}
