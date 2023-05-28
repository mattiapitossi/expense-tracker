package com.expensetracker.service.impl;

import com.expensetracker.model.dto.response.*;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.mapper.ExpenseMapper;
import com.expensetracker.model.*;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.SubcategoryRepository;
import com.expensetracker.repository.WalletRepository;
import com.expensetracker.service.BudgetMovementService;
import com.expensetracker.service.BudgetService;
import com.expensetracker.type_enum.TypeTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ExpenseServiceImpl implements com.expensetracker.service.ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final CategoryRepository categoryRepository;

    private final WalletRepository walletRepository;

    private final SubcategoryRepository subcategoryRepository;

    private final ExpenseMapper expenseMapper;

    private final BudgetService budgetService;

    private final BudgetMovementService budgetMovementService;

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
    public ExpenseResponseDTO duplicateExpense(Long expenseId) {
        Expense oldExpense = expenseRepository.findById(expenseId).get();
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(oldExpense, newExpense);

        newExpense.setId(null);
        setAdditionalFields(newExpense);

        expenseRepository.save(newExpense);

        ExpenseResponseDTO expenseResponseDTO = expenseMapper.expenseToExpenseResponse(newExpense);

        budgetMovementService.duplicateBudgetsMovements(expenseResponseDTO, oldExpense, newExpense);

        return expenseResponseDTO;
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
    public Map<Long, SubcategoryYearRecapDTO> getSubcategoriesExpensesBy(int year) {
        LocalDate localDate = LocalDate.now().withYear(year);
        LocalDate startDate = localDate.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endDate = localDate.with(TemporalAdjusters.lastDayOfYear());
        Map<Long, SubcategoryYearRecapDTO> recapDTOMap = new HashMap<>();

        List<SubcategoryExpensesDTO> subcategoryExpensesDTOS = expenseRepository.getSubcategoryExpensesBy(startDate, endDate);
        subcategoryExpensesDTOS.forEach(subcategoryExpensesDTO -> {
            if(recapDTOMap.containsKey(subcategoryExpensesDTO.subcategory().getId())) {
                recapDTOMap.get(subcategoryExpensesDTO.subcategory().getId()).setMonth(subcategoryExpensesDTO.month(), subcategoryExpensesDTO.value());
            } else {
                SubcategoryYearRecapDTO dto = new SubcategoryYearRecapDTO(subcategoryExpensesDTO.subcategory());
                dto.setMonth(subcategoryExpensesDTO.month(), subcategoryExpensesDTO.value());
                recapDTOMap.put(subcategoryExpensesDTO.subcategory().getId(), dto);
            }
        });

        return recapDTOMap;
    }

    @Override
    public void deleteExpenseById(Long expenseId) {
        Expense expense = expenseRepository.getReferenceById(expenseId);
        budgetMovementService.modifyBudgets(expense);
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponseDTO createNewExpense(ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = expenseMapper.expenseRequestToExpense(expenseRequestDTO);

        setAdditionalFields(expense);

        ExpenseResponseDTO expenseResponseDTO = expenseMapper.expenseToExpenseResponse(expense);

        expenseRepository.save(expense);

        budgetsCreateOrUpdate(expenseRequestDTO, expense, expenseResponseDTO);
        return expenseResponseDTO;
    }

    @Override
    public ExpenseResponseDTO modifyExpense(ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = expenseMapper.expenseRequestToExpense(expenseRequestDTO);
        expense.setId(expenseRequestDTO.getId());
        Expense oldExpense = expenseRepository.findById(expenseRequestDTO.getId()).get();

        setAdditionalFields(expense);
        BeanUtils.copyProperties(expense, oldExpense);
        expenseRepository.save(oldExpense);

        budgetMovementService.modifyBudgets(oldExpense);

        ExpenseResponseDTO expenseResponseDTO = expenseMapper.expenseToExpenseResponse(oldExpense);
        budgetsCreateOrUpdate(expenseRequestDTO, expense, expenseResponseDTO);

        return expenseResponseDTO;
    }

    @Override
    public void saveExpenseFromPeriod(ExpensePeriod expensePeriod) {
        Expense expense = expenseMapper.periodToExpense(expensePeriod);
        expenseRepository.save(expense);
    }

    private void setAdditionalFields(Expense expense) {
        Category category = categoryRepository.findByName(expense.getCategory().getName());
        expense.setCategory(category);

        if (expense.getSubcategory() == null) {
            expense.setSubcategory((Subcategory) null);
        } else {
            Subcategory subcategory = subcategoryRepository.findByNameAndCategory(expense.getSubcategory().getName(), category);
            expense.setSubcategory(subcategory);
        }

        Wallet wallet = walletRepository.findByName(expense.getWallet().getName());
        expense.setWallet(wallet);
    }

    private void budgetsCreateOrUpdate(ExpenseRequestDTO expenseRequestDTO, Expense expense, ExpenseResponseDTO expenseResponseDTO) {
        if(TypeTransaction.IN.name().equalsIgnoreCase(expenseRequestDTO.getTypeOfTransaction())) {
            budgetService.createOrUpdateBudget(expenseRequestDTO);
            budgetMovementService.createInBudgetMovement(expense, expenseRequestDTO);
        } else {
            budgetService.updateBudget(expenseResponseDTO, expense);
            budgetMovementService.createOutBudgetMovement(expense);
        }
    }
}
