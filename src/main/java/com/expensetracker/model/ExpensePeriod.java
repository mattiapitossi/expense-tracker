package com.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="expense_period")
@NotNull
public class ExpensePeriod {

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer id;

    @Column(name = "start_date")
    public LocalDateTime startDate;

    @Column(name = "end_date")
    public LocalDateTime endDate;

    @Column(name = "period")
    public LocalTime period;

    @OneToOne
    @JoinColumn(name = "expense_id")
    public Expense expense;
}
