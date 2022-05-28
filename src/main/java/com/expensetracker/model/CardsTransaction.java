package com.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "cards_transactions")
public class CardsTransaction implements Serializable {

    private static final long serialVersionUID = 42320230496L;

    @Id
    @JoinColumn(name = "card_id")
    @ManyToOne
    public Card card;

    @Id
    @JoinColumn(name = "expense_id")
    @OneToOne
    public Expense expense;

}

