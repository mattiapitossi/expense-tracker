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
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "wallet_id")
    @ManyToOne
    public Wallet wallet;

    @JoinColumn(name = "file_id")
    @ManyToOne
    public File file;

    @JoinColumn(name = "expense_id")
    @OneToOne
    public Expense expense;

}

