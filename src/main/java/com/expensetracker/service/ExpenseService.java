package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.model.Subcategory;
import com.expensetracker.model.Wallet;
import com.expensetracker.model.dto.response.*;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;

import java.util.List;
import java.util.Map;

public interface ExpenseService {
    boolean expenseWithCategoryExist(Long categoryId);

    boolean expenseWithWalletExist(Long walletId);

    List<Expense> expensesLinkedToWallet(Wallet wallet);

    ExpenseResponseDTO duplicateExpense(Long expenseId);

    List<Expense> getAllExpenses();

    List<Expense> getAllExpensesPeriodByDate(int month, int year);

    List<YearExpensesDTO> getAllExpensesBy(int year);

    List<CategoryExpensesDTO> getCategoriesExpensesBy(int month, int year);

    Map<Long, SubcategoryYearRecapDTO> getSubcategoriesExpensesBy(int year);

    void deleteExpenseById(Long expenseId);

    ExpenseResponseDTO createNewExpense(ExpenseRequestDTO expense);

    ExpenseResponseDTO modifyExpense(ExpenseRequestDTO expense);

    void saveExpenseFromPeriod(ExpensePeriod expensePeriod);
}
