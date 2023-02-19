package com.expensetracker.service.impl;

import com.expensetracker.model.dto.CategoryExpensesDTO;
import com.expensetracker.model.dto.YearExpensesDTO;
import com.expensetracker.mapper.ExpenseMapper;
import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements com.expensetracker.service.ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final WalletRepository walletRepository;

    private final SubcategoryRepository subcategoryRepository;

    private final ExpenseMapper expenseMapper;

    @Override
    public boolean expenseWithCategoryExist(Long categoryId) {
        return expenseRepository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean expenseWithWalletExist(Long walletId) {
        return expenseRepository.existsByWalletId(walletId);
    }

    @Override
    public List<Expense> expensesLinkedToWallet(Wallet wallet) {
        return expenseRepository.getExpenseByWallet(wallet);
    }

    @Override
    public Expense duplicateExpense(Long expenseId) {
        Expense oldExpense = expenseRepository.findById(expenseId).get();
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(oldExpense, newExpense);

        newExpense.setId(null);
        newExpense.setSubcategory(new Subcategory());
        return createNewExpense(newExpense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getAllExpensesPeriodByDate(int month, int year) {
        LocalDate localDate = LocalDate.now().withMonth(month).withYear(year);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());

        return expenseRepository.getExpenseByExpenseDateBetween(startDate, endDate);
    }

    @Override
    public List<YearExpensesDTO> getAllExpensesBy(int year) {
        LocalDate localDate = LocalDate.now().withYear(year);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfYear());

        return expenseRepository.getExpensesBy(startDate, endDate);
    }

    @Override
    public List<CategoryExpensesDTO> getCategoriesExpensesBy(int month, int year) {
        LocalDate localDate = LocalDate.now().withMonth(month).withYear(year);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfMonth());

        return expenseRepository.getCategoryExpensesBy(startDate, endDate);
    }

    @Override
    public void deleteExpenseById(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public Expense createNewExpense(Expense expense) {
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        expense.setCategory(category);

        if (expense.getSubcategory().getName() == null) {
            expense.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByNameAndCategory(expense.getSubcategory().getName(), category);
            expense.setSubcategory(subcategory);
        }

        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        expense.setWallet(wallet);

        expenseRepository.save(expense);

        return expense;
    }

    @Override
    public Expense modifyExpense(Expense expense) {
        Expense oldExpense = expenseRepository.findById(expense.getId()).get();
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());

        if (expense.getSubcategory().getName() == null) {
            expense.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByNameAndCategory(expense.getSubcategory().getName(), category);
            expense.setSubcategory(subcategory);
        }

        expense.setCategory(category);
        expense.setWallet(wallet);
        BeanUtils.copyProperties(expense, oldExpense);

        return expenseRepository.save(oldExpense);
    }

    @Override
    public void saveExpenseFromPeriod(ExpensePeriod expensePeriod) {
        Expense expense = expenseMapper.periodToExpense(expensePeriod);
        expenseRepository.save(expense);
    }
}
