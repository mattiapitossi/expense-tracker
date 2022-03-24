package com.mattiap.expensetracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "category")
public class Category {

    @Id
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
}
