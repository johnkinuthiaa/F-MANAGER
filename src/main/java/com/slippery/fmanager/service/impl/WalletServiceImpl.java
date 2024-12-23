package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.WalletDto;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.repository.WalletRepository;
import com.slippery.fmanager.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WalletDto createNewWallet(Wallet wallet) {
        WalletDto response =new WalletDto();

        walletRepository.save(wallet);
        response.setMessage("wallet created!");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public WalletDto getWalletByUserId(Long userId) {
        WalletDto response =new WalletDto();
        List<Wallet> wallets =new ArrayList<>();
        Wallet userWallet =walletRepository.findByUsers_Id(userId);

        if (userWallet ==null){
            response.setMessage("wallet not found");
            response.setStatusCode(200);
        }else{
            wallets.add(userWallet);
            response.setMessage("wallet for usr found");
            response.setWallets(wallets);
            response.setStatusCode(200);
        }
        return response;
    }

    @Override
    public WalletDto deleteWallet(Long userId) {
        return null;
    }
}
