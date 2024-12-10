package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.WalletDto;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.WalletRepository;
import com.slippery.fmanager.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletDto createNewWallet(Wallet wallet) {
        WalletDto response =new WalletDto();
        walletRepository.save(wallet);
        response.setMessage("wallet created!");

        return response;
    }

    @Override
    public WalletDto deleteWallet(Long userId) {
        return null;
    }
}
