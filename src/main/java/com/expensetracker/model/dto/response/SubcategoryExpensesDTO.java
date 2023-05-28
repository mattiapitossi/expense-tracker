package com.expensetracker.model.dto.response;

import com.expensetracker.model.Category;
import com.expensetracker.model.Subcategory;

import java.math.BigDecimal;

public record SubcategoryExpensesDTO(Subcategory subcategory, BigDecimal value, int month) {
}
