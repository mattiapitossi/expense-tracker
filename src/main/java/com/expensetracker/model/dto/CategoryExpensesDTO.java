package com.expensetracker.model.dto;

import com.expensetracker.model.Category;

import java.math.BigDecimal;

public record CategoryExpensesDTO(Category category, BigDecimal value) {
}
