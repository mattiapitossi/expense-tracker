package com.expensetracker.cronjobs;

import com.expensetracker.model.ExpensePeriod;
import com.expensetracker.service.ExpensePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ExpensesCronJobs {

    private final ExpensePeriodService expensePeriodService;

    @PostConstruct
    private void verifyExpensesPeriod() {
        List<ExpensePeriod> expensePeriodList = expensePeriodService.getAllActiveExpensesPeriod();

        expensePeriodList.forEach(expensePeriodService::createExpenses);
    }


}
