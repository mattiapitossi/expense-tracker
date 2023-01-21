package com.expensetracker.service.impl;

import com.expensetracker.model.*;
import com.expensetracker.repository.*;
import com.expensetracker.service.ExpensePeriodService;
import com.expensetracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpensePeriodServiceImpl implements ExpensePeriodService {

    private final ExpensePeriodRepository expensePeriodRepository;

    private final ExpenseService expenseService;

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
            Subcategory subcategory = subcategoryRepository.findByNameAndCategory(expensePeriod.getSubcategory().getName(), category);
            expensePeriod.setSubcategory(subcategory);
        }

        Wallet wallet = walletRepository.findByName(expensePeriod.getWallet().getName());
        expensePeriod.setWallet(wallet);

        expensePeriod.setNextPayment(expensePeriod.getStartDate());

        createExpenses(expensePeriod);

        return expensePeriodRepository.save(expensePeriod);
    }

    @Override
    public void createExpenses(ExpensePeriod expensePeriod) {
        while (expensePeriod.getNextPayment() != null && !expensePeriod.getNextPayment().isAfter(LocalDate.now())) {
            expenseService.saveExpenseFromPeriod(expensePeriod);

            setExpenseNextPayment(expensePeriod);
            expensePeriodRepository.save(expensePeriod);
        }
    }

    private void setExpenseNextPayment(ExpensePeriod expensePeriod) {
        LocalDate nextPayment = switch (expensePeriod.getPeriodType()) {
            case "YEAR" -> expensePeriod.getNextPayment().plusYears(expensePeriod.getPeriodDate());

            case "MONTH" -> expensePeriod.getNextPayment().plusMonths(expensePeriod.getPeriodDate());

            case "DAY" -> expensePeriod.getNextPayment().plusDays(expensePeriod.getPeriodDate());

            default -> null;
        };

        if (expensePeriod.getEndDate() != null && expensePeriod.getEndDate().isBefore(nextPayment)) {
            expensePeriod.setNextPayment(null);
            return;
        }

        expensePeriod.setNextPayment(nextPayment);
    }

    @Override
    public List<ExpensePeriod> getAllExpensesPeriod() {
        return expensePeriodRepository.findAll();
    }

    @Override
    public List<ExpensePeriod> getAllActiveExpensesPeriod() {
        return expensePeriodRepository.getExpensePeriodsByNextPaymentIsNotNull();
    }


    @Override
    public ExpensePeriod modifyExpensePeriod(ExpensePeriod expensePeriod) {
        ExpensePeriod oldExpense = expensePeriodRepository.findById(expensePeriod.getId()).get();
        Category category = categoryRepository.findByName(expensePeriod.getCategory().getName());
        Wallet wallet = walletRepository.findByName(expensePeriod.getWallet().getName());

        if (expensePeriod.getSubcategory().getName() == null) {
            expensePeriod.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByNameAndCategory(expensePeriod.getSubcategory().getName(), category);
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
    public ExpensePeriod duplicateExpensePeriod(Long expenseId) {
        ExpensePeriod oldExpense = expensePeriodRepository.findById(expenseId).get();
        ExpensePeriod newExpense = new ExpensePeriod();
        BeanUtils.copyProperties(oldExpense, newExpense);

        newExpense.setId(null);
        newExpense.setSubcategory(new Subcategory());
        return createNewExpensePeriod(newExpense);
    }

    @Override
    public void deleteExpensePeriodById(Long expenseId) {
        expensePeriodRepository.deleteById(expenseId);
    }
}
