package com.expensetracker.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BudgetDTO {

    private String category;

    private double value;
}
