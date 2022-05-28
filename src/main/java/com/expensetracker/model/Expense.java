package com.expensetracker.model;

import com.expensetracker.type_enum.TypePayment;
import com.expensetracker.type_enum.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime expenseDate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "type_of_transaction")
    @Enumerated(EnumType.ORDINAL)
    private TypeTransaction typeOfTransaction;

    @Column(name = "type_of_payment")
    @Enumerated(EnumType.ORDINAL)
    private TypePayment typeOfPayment;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
