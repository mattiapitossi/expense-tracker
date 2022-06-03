package com.expensetracker.service;

import com.expensetracker.model.Wallet;
import com.expensetracker.model.WalletTransaction;
import com.expensetracker.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    public void createWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

    public void modifyWalletTransaction(WalletTransaction walletTransaction) {
        WalletTransaction oldWalletTransaction = walletTransactionRepository.findById(walletTransaction.getId()).get();

        BeanUtils.copyProperties(walletTransaction, oldWalletTransaction);
    }

    public void deleteWalletTransaction(Wallet wallet) {
        walletTransactionRepository.deleteByWallet(wallet);
    }

    public WalletTransaction findByWallet(Wallet wallet) {
        return walletTransactionRepository.findByWallet(wallet);
    }
}
