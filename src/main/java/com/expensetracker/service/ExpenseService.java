package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.model.Wallet;
import com.expensetracker.model.YearExpensesDTO;

import java.util.List;
import java.util.Map;

public interface ExpenseService {
    boolean expenseWithCategoryExist(Integer categoryId);

    boolean expenseWithWalletExist(Integer walletId);

    List<Expense> expensesLinkedToWallet(Wallet wallet);

    Expense duplicateExpense(Integer expenseId);

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesPeriodByDate(int month, int year);

    List<YearExpensesDTO> getAllExpensesByYear(int year);

    void deleteExpenseById(Integer expenseId);

    Expense createNewExpense(Expense expense);

    Expense modifyExpense(Expense expense);

    void saveExpenseFromPeriod(ExpensePeriod expensePeriod);
}
