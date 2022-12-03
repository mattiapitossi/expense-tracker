package com.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

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

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "type_of_transaction")
//  TODO  @Enumerated(EnumType.ORDINAL)
    private String typeOfTransaction;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @JsonSetter
    public void setCategory(String category) {
        this.category = new Category();
        this.category.setName(category);
    }

    public void setCategory(Category category) { this.category = category; }

    @JsonSetter
    public void setSubcategory(String subcategory) {
        this.subcategory = new Subcategory();
        this.subcategory.setName(subcategory);
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @JsonSetter
    public void setWallet(String wallet) {
        this.wallet = new Wallet();
        this.wallet.setName(wallet);
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
