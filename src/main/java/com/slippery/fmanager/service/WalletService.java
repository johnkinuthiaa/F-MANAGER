package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.WalletDto;
import com.slippery.fmanager.models.Wallet;

public interface WalletService {
    WalletDto createNewWallet(Wallet wallet);
    WalletDto getWalletByUserId(Long userId);
    WalletDto deleteWallet(Long userId);
}
