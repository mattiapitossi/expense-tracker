package com.expensetracker.controller;

import com.expensetracker.model.Wallet;
import com.expensetracker.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/wallets")
    ResponseEntity<List<Wallet>> readWallets() {

        List<Wallet> walletList = walletService.getWallets();

        for(Wallet wallet : walletList) {
            wallet.setValue(walletService.getValue(wallet));
        }

        return ResponseEntity.ok().body(walletList);
    }

    @PostMapping("/wallets")
    ResponseEntity<Wallet> createWallet(@Valid @RequestBody Wallet wallet) throws URISyntaxException {
        var res = walletService.createWallet(wallet);
        return ResponseEntity.created(new URI("/api/wallets" + res.getId())).body(res);
    }

    @PutMapping("/wallets")
    ResponseEntity<Wallet> updateWallet(@Valid @RequestBody Wallet wallet) {
        var res = walletService.updateWallet(wallet);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/wallets/{id}")
    ResponseEntity<?> deleteWallet(@PathVariable Integer id) {
        walletService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
