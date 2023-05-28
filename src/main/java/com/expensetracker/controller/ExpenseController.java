package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Subcategory;
import com.expensetracker.model.dto.response.*;
import com.expensetracker.model.dto.request.ExpenseRequestDTO;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    ResponseEntity<List<Expense>> getExpenses() {
        return ResponseEntity.ok().body(expenseService.getAllExpenses());
    }


    @GetMapping("/expenses/date")
    ResponseEntity<List<Expense>> getExpensesBetween(@RequestParam(name = "month") @Range(min = 1, max = 12) int month,
                                                     @RequestParam(name = "year") @Range(min = 2020, max = 2099) int year) {
        return ResponseEntity.ok().body(expenseService.getAllExpensesPeriodByDate(month, year));
    }

    @GetMapping("/expenses/categories")
    ResponseEntity<List<CategoryExpensesDTO>> getTotalExpensesValueByCategories(@RequestParam(name = "month") @Range(min = 1, max = 12) int month,
                                                                                @RequestParam(name = "year") @Range(min = 2000, max = 2099) int year) {
        return ResponseEntity.ok().body(expenseService.getCategoriesExpensesBy(month, year));
    }

    @GetMapping("/expenses/subcategories")
    ResponseEntity<Map<Long, SubcategoryYearRecapDTO>> getYearRecapBySubcategories(@RequestParam(name = "year") @Range(min = 2000, max = 2099) int year) {
        return ResponseEntity.ok().body(expenseService.getSubcategoriesExpensesBy(year));
    }


    @GetMapping("/expenses/{year:^20[2-9]\\d$}")
    ResponseEntity<List<YearExpensesDTO>> getExpensesBetween(@PathVariable(name = "year") int year) {
        return ResponseEntity.ok().body(expenseService.getAllExpensesBy(year));
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/expenses")
    ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO expense) throws URISyntaxException {
        ExpenseResponseDTO result = expenseService.createNewExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PostMapping("/expenses/{id}")
    ResponseEntity<ExpenseResponseDTO> duplicateExpense(@PathVariable(name = "id") Long expenseId) throws URISyntaxException {
        ExpenseResponseDTO result = expenseService.duplicateExpense(expenseId);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PutMapping("/expenses")
    ResponseEntity<ExpenseResponseDTO> modifyExpense(@Valid @RequestBody ExpenseRequestDTO expense) throws URISyntaxException {
        ExpenseResponseDTO result = expenseService.modifyExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

}
