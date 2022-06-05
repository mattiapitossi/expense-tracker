package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final WalletRepository walletRepository;

    private final SubcategoryRepository subcategoryRepository;

    private final WalletTransactionService walletTransactionService;

    public boolean expenseWithCategoryExist(Integer categoryId) {
        return expenseRepository.existsByCategory_Id(categoryId);
    }

    public boolean expenseWithWalletExist(Integer walletId) {
        return expenseRepository.existsByWallet_Id(walletId);
    }

    public List<Expense> expensesLinkedToWallet(Wallet wallet) {
        return expenseRepository.getExpenseByWallet(wallet);
    }

    public Expense duplicateExpense(Integer expenseId) {
        Expense oldExpense = expenseRepository.findById(expenseId).get();
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(oldExpense, newExpense);

        newExpense.setId(null);
        newExpense.setSubcategory(new Subcategory());
        return createNewExpense(newExpense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void deleteExpenseById(Integer expenseId) {
        walletTransactionService.deleteWalletTransactionByExpenseId(expenseId);
        expenseRepository.deleteById(expenseId);
    }

    public Expense createNewExpense(Expense expense) {
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        expense.setCategory(category);

        if (expense.getSubcategory().getName() == null) {
            expense.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByName(expense.getSubcategory().getName());
            expense.setSubcategory(subcategory);
        }

        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        expense.setWallet(wallet);

        expenseRepository.save(expense);

        //TODO add files to wallet transaction
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setExpense(expense);
        walletTransaction.setWallet(wallet);

        walletTransactionService.createWalletTransaction(walletTransaction);

        return expense;
    }

    public Expense modifyExpense(Expense expense) {
        Expense oldExpense = expenseRepository.findById(expense.getId()).get();
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        WalletTransaction walletTransaction = walletTransactionService.findByExpenseId(oldExpense.getId());

        if (expense.getSubcategory().getName() == null) {
            expense.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByName(expense.getSubcategory().getName());
            expense.setSubcategory(subcategory);
        }

        //check if wallet in walletTransaction has been changed
        if (walletTransaction.getWallet().getId() != wallet.getId()) {
            walletTransaction.setWallet(wallet);
            walletTransactionService.modifyWalletTransaction(walletTransaction);
        }

        expense.setCategory(category);
        expense.setWallet(wallet);
        System.out.println(expense.getCategory());
        System.out.println(expense.getWallet());
        BeanUtils.copyProperties(expense, oldExpense);

        return expenseRepository.save(oldExpense);
    }
}
