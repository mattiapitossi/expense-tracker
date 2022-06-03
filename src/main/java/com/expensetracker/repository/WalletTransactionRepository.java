package com.expensetracker.repository;

import com.expensetracker.model.Wallet;
import com.expensetracker.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {

    WalletTransaction findByWallet(Wallet wallet);

    WalletTransaction findByExpenseId(Integer expenseId);

    void deleteByWallet(Wallet wallet);

}
