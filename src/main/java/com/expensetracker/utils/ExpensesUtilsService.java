package com.expensetracker.utils;

import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.service.ExpensePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpensesUtilsService {

    private final ExpensePeriodService expensePeriodService;

    @PostConstruct
    private void verifyExpensesPeriod() {
        List<ExpensePeriod> expensePeriodList = expensePeriodService.getAllActiveExpensesPeriod();

        expensePeriodList.forEach(expensePeriodService::createExpenses);
    }


}
