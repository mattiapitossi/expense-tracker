package com.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="cards")
public class Card {

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer id;

    @Column(name = "nome")
    public String nome;

    @Column(name = "last_four_digits")
    public String lastFourDigits;
}
