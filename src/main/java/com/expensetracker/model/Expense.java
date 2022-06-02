package com.expensetracker.model;

import com.expensetracker.type_enum.TypePayment;
import com.expensetracker.type_enum.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "expenses")
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("expense_date")
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "type_of_transaction")
//  TODO  @Enumerated(EnumType.ORDINAL)
    private String typeOfTransaction;

    @Column(name = "type_of_payment")
//  TODO  @Enumerated(EnumType.ORDINAL)
    private String typeOfPayment;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonSetter
    public void setCategory(String category) {
        this.category = new Category();
        this.category.setName(category);
    }

    public void setCategory(Category category) { this.category = category; }
}
