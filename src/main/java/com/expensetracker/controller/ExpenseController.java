package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.impl.ExpenseServiceImpl;
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
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses")
    ResponseEntity<List<Expense>> getExpenses() {
        return ResponseEntity.ok().body(expenseService.getAllExpenses());
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Integer id) {
        expenseService.deleteExpenseById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseService.createNewExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PostMapping("/expenses/{id}")
    ResponseEntity<Expense> duplicateExpense(@PathVariable(name = "id") Integer expenseId) throws URISyntaxException {
        Expense result = expenseService.duplicateExpense(expenseId);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

    @PutMapping("/expenses")
    ResponseEntity<Expense> modifyExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseService.modifyExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }

}
