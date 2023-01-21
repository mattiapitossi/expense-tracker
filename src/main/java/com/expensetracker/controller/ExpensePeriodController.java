package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.service.impl.ExpensePeriodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpensePeriodController {

    private final ExpensePeriodServiceImpl expensePeriodService;

    @GetMapping("/period")
    ResponseEntity<List<ExpensePeriod>> getExpenses() {
        return ResponseEntity.ok().body(expensePeriodService.getAllExpensesPeriod());
    }

    @DeleteMapping("/period/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expensePeriodService.deleteExpensePeriodById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/period")
    ResponseEntity<ExpensePeriod> createExpensePeriod(@Valid @RequestBody ExpensePeriod expensePeriod) throws URISyntaxException {
        ExpensePeriod result = expensePeriodService.createNewExpensePeriod(expensePeriod);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }

    @PostMapping("/period/{id}")
    ResponseEntity<ExpensePeriod> duplicateExpense(@PathVariable(name = "id") Long expenseId) throws URISyntaxException {
        ExpensePeriod result = expensePeriodService.duplicateExpensePeriod(expenseId);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }

    @PutMapping("/period")
    ResponseEntity<ExpensePeriod> modifyExpense(@Valid @RequestBody ExpensePeriod expense) throws URISyntaxException {
        ExpensePeriod result = expensePeriodService.modifyExpensePeriod(expense);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }
}
