package com.expensetracker.utils;

import com.expensetracker.model.Budget;
import com.expensetracker.service.BudgetService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BudgetsUtilsService {

    private final BudgetService budgetService;

    @PostConstruct
    private void verifyLastMonthBudget() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        List<Budget> budgetList = budgetService.getAllBudgetsBy(month, year);

        calendar.add(Calendar.MONTH, 1);

        budgetList.forEach(budget -> budgetService.createBudgetFromBudget(budget, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
    }


}
