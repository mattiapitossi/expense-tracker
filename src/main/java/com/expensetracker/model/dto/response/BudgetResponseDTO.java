package com.expensetracker.model.dto.response;

import java.math.BigDecimal;

public record BudgetResponseDTO(String category, BigDecimal totalValue, BigDecimal remainingValue, Integer month, Integer year) {
}
