package com.expensetracker.service.impl;

import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpensePeriodRepository;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.repository.WalletRepository;
import com.expensetracker.service.ExpensePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
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


}
