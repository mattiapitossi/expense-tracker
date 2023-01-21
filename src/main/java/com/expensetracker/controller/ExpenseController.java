package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.YearExpensesDTO;
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


    @GetMapping("/expenses/{year:^20[2-9]\\d$}")
    ResponseEntity<List<YearExpensesDTO>> getExpensesBetween(@PathVariable(name = "year") int year) {
        return ResponseEntity.ok().body(expenseService.getAllExpensesByYear(year));
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseService.createNewExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PostMapping("/expenses/{id}")
    ResponseEntity<Expense> duplicateExpense(@PathVariable(name = "id") Long expenseId) throws URISyntaxException {
        Expense result = expenseService.duplicateExpense(expenseId);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PutMapping("/expenses")
    ResponseEntity<Expense> modifyExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseService.modifyExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

}
