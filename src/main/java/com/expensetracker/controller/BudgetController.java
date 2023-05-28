package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.model.dto.response.BudgetResponseDTO;
import com.expensetracker.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/budgets")
    ResponseEntity<List<Budget>> readBudgets() {
        return ResponseEntity.ok().body(budgetService.getAllBudgets());
    }

    @GetMapping("/budgets/date")
    ResponseEntity<List<BudgetResponseDTO>> getBudgetsBy(@RequestParam(name = "month") @Range(min = 1, max = 12) int month,
                                                         @RequestParam(name = "year") @Range(min = 2020, max = 2099) int year) {
        return ResponseEntity.ok().body(budgetService.getAllBudgetsDTOBy(month, year));
    }
}
