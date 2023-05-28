package com.expensetracker.model.dto.request;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Subcategory;
import com.expensetracker.model.Wallet;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class ExpenseRequestDTO extends Expense {

    private List<BudgetDTO> budgets;
}
