package com.expensetracker.service.impl;

import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpensePeriodRepository;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.repository.WalletRepository;
import com.expensetracker.service.ExpensePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpensePeriodServiceImpl implements ExpensePeriodService {

    private final ExpensePeriodRepository expensePeriodRepository;

    private final CategoryRepository categoryRepository;

    private final WalletRepository walletRepository;

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public ExpensePeriod createNewExpensePeriod(ExpensePeriod expensePeriod) {
        Category category = categoryRepository.findByName(expensePeriod.getCategory().getName());
        expensePeriod.setCategory(category);

        if (expensePeriod.getSubcategory().getName() == null) {
            expensePeriod.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByName(expensePeriod.getSubcategory().getName());
            expensePeriod.setSubcategory(subcategory);
        }

        Wallet wallet = walletRepository.findByName(expensePeriod.getWallet().getName());
        expensePeriod.setWallet(wallet);

        return expensePeriodRepository.save(expensePeriod);
    }

    @Override
    public List<ExpensePeriod> getAllExpensesPeriod() {
        return expensePeriodRepository.findAll();
    }



    @Override
    public ExpensePeriod modifyExpensePeriod(ExpensePeriod expensePeriod) {
        ExpensePeriod oldExpense = expensePeriodRepository.findById(expensePeriod.getId()).get();
        Category category = categoryRepository.findByName(expensePeriod.getCategory().getName());
        Wallet wallet = walletRepository.findByName(expensePeriod.getWallet().getName());

        if (expensePeriod.getSubcategory().getName() == null) {
            expensePeriod.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByName(expensePeriod.getSubcategory().getName());
            expensePeriod.setSubcategory(subcategory);
        }

        expensePeriod.setCategory(category);
        expensePeriod.setWallet(wallet);
        System.out.println(expensePeriod.getCategory());
        System.out.println(expensePeriod.getWallet());
        BeanUtils.copyProperties(expensePeriod, oldExpense);

        return expensePeriodRepository.save(oldExpense);
    }


      @Override
    public ExpensePeriod duplicateExpensePeriod(Integer expenseId) {
        ExpensePeriod oldExpense = expensePeriodRepository.findById(expenseId).get();
        ExpensePeriod newExpense = new ExpensePeriod();
        BeanUtils.copyProperties(oldExpense, newExpense);

        newExpense.setId(null);
        newExpense.setSubcategory(new Subcategory());
        return createNewExpensePeriod(newExpense);
    }

    @Override
    public void deleteExpensePeriodById(Integer expenseId) {
        expensePeriodRepository.deleteById(expenseId);
    }
}
