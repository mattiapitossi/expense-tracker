package com.expensetracker.service;

import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
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

    private final WalletTransactionService walletTransactionService;

    public boolean expenseWithCategoryExist(Integer categoryId) {
        return expenseRepository.existsByCategory_Id(categoryId);
    }

    public boolean expenseWithWalletExist(Integer walletId) {
        return expenseRepository.existsByWallet_Id(walletId);
    }

    public Expense duplicateExpense(Integer expenseId) {
        Expense oldExpense = expenseRepository.findById(expenseId).get();
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(oldExpense, newExpense);

        WalletTransaction walletTransaction = walletTransactionService.findByWallet(newExpense.getWallet());
        walletTransaction.setExpense(newExpense);
        walletTransactionService.createWalletTransaction(walletTransaction);

        newExpense.setId(null);
        return expenseRepository.save(newExpense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void deleteExpenseById(Integer id) {
        walletTransactionService.deleteWalletTransaction(expenseRepository.getById(id).getWallet());
        expenseRepository.deleteById(id);
    }

    public Expense createNewExpense(Expense expense) {
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        expense.setCategory(category);

        if (expense.getSecondaryCategory().getName() == null) {
            expense.setSecondaryCategory((SecondaryCategory) null);
        } else {
            //TODO add secondaryCategory
        }

        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        expense.setWallet(wallet);

        Expense expense1 = expenseRepository.save(expense);

        //TODO add files to wallet transaction
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setExpense(expense);
        walletTransaction.setWallet(wallet);

        walletTransactionService.createWalletTransaction(walletTransaction);

        return expense1;
    }

    public Expense modifyExpense(Expense expense) {
        Expense oldExpense = expenseRepository.findById(expense.getId()).get();
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        WalletTransaction walletTransaction = walletTransactionService.findByExpenseId(oldExpense.getId());

        if (expense.getSecondaryCategory().getName() == null) {
            expense.setSecondaryCategory((SecondaryCategory) null);
        } else {
            //TODO add secondaryCategory
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
