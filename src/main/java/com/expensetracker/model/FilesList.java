package com.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "cards_transactions")
public class FilesList implements Serializable {

    private static final long serialVersionUID = 42320230496L;

    @Id
    @JoinColumn(name = "file_id")
    @ManyToOne
    private File file;

    @Id
    @JoinColumn(name = "expense_id")
    @OneToOne
    private Expense expense;

}
