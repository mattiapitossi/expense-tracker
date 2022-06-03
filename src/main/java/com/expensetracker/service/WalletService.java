package com.expensetracker.service;

import com.expensetracker.model.Wallet;
import com.expensetracker.repository.WalletRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    private final ExpenseService expenseService;

    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet updateWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void deleteById(Integer walletId) {
        if(!expenseService.expenseWithWalletExist(walletId)) {
            walletRepository.deleteById(walletId);
        } else {
            //TODO throw clientError
        }
    }
}
