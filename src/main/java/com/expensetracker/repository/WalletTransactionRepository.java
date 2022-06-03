package com.expensetracker.repository;

import com.expensetracker.model.Wallet;
import com.expensetracker.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Integer> {

    WalletTransaction findByWallet(Wallet wallet);

    WalletTransaction findByExpenseId(Integer expenseId);

    void deleteByWallet(Wallet wallet);

    @Transactional
    void deleteByExpenseId(Integer expenseId);
}
