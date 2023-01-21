package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.YearExpensesDTO;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    ResponseEntity<List<Expense>> getExpenses() {
        return ResponseEntity.ok().body(expenseService.getAllExpenses());
    }


    @GetMapping("/expenses/date")
    ResponseEntity<List<Expense>> getExpensesBetween(@RequestParam(name = "month") int month,
                                                     @RequestParam(name = "year") int year) {
        return ResponseEntity.ok().body(expenseService.getAllExpensesPeriodByDate(month, year));
    }


    @GetMapping("/expenses/{year}")
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
