package com.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "expenses")
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @JsonProperty("expense_date")
    @Column(name = "expense_date")
    private Instant expenseDate;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "type_of_transaction")
    private String typeOfTransaction;

    @Column(name = "type_of_payment")
    private String typeOfPayment;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToOne
    private User user;
}
