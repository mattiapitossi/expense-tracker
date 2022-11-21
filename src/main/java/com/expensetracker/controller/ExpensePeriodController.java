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

    @PostMapping("/period")
    ResponseEntity<ExpensePeriod> createExpensePeriod(@Valid @RequestBody ExpensePeriod expensePeriod) throws URISyntaxException {
        System.out.println("EXPENSE PERIODDDDDD _____ :::::::" + expensePeriod);
        ExpensePeriod result = expensePeriodService.createNewExpensePeriod(expensePeriod);
        return ResponseEntity.created(new URI("/api/expense/period" + result.getId())).body(result);
    }
}