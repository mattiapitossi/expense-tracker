package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.service.impl.ExpensePeriodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpensePeriodController {

    private final ExpensePeriodServiceImpl expensePeriodService;

    @GetMapping("/period")
    List<ExpensePeriod> getExpenses() {
        return expensePeriodService.getAllExpensesPeriod();
    }

    @DeleteMapping("/period/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Integer id) {
        expensePeriodService.deleteExpensePeriodById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/period")
    ResponseEntity<ExpensePeriod> createExpensePeriod(@Valid @RequestBody ExpensePeriod expensePeriod) throws URISyntaxException {
        System.out.println("EXPENSE PERIOD _____ :::::::" + expensePeriod);
        ExpensePeriod result = expensePeriodService.createNewExpensePeriod(expensePeriod);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }

    @PostMapping("/period/{id}")
    ResponseEntity<ExpensePeriod> duplicateExpense(@PathVariable(name = "id") Integer expenseId) throws URISyntaxException {
        System.out.println(expenseId);
        ExpensePeriod result = expensePeriodService.duplicateExpensePeriod(expenseId);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }

    @PutMapping("/period")
    ResponseEntity<ExpensePeriod> modifyExpense(@Valid @RequestBody ExpensePeriod expense) throws URISyntaxException {
        System.out.println(expense);
        ExpensePeriod result = expensePeriodService.modifyExpensePeriod(expense);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }
}
