package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.SavingsAccountDto;
import com.slippery.fmanager.models.SavingsAccount;
import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.SavingsRepository;
import com.slippery.fmanager.repository.TransactionsRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.repository.WalletRepository;
import com.slippery.fmanager.service.SavingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SavingsAccountServiceImpl implements SavingsService {
    private final SavingsRepository repository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionsRepository transactionsRepository;

    public SavingsAccountServiceImpl(SavingsRepository repository, UserRepository userRepository, WalletRepository walletRepository, TransactionsRepository transactionsRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionsRepository = transactionsRepository;
    }

    private UUID generateUUID(){
        return UUID.randomUUID();
    }

    @Override
    public SavingsAccountDto createNewSavingsAccount(SavingsAccount accountDetails,Long userId) {
        SavingsAccountDto response =new SavingsAccountDto();
        SavingsAccount account =new SavingsAccount();
        Optional<User> user =userRepository.findById(userId);
        Wallet wallet =walletRepository.findById(userId).get();
        if(user.isEmpty()){
            throw new RuntimeException("user not found");
        }
        try {
            account.setAmount(0L);
            account.setCreatedOn(LocalDateTime.now());
            account.setSavingAccountNumber(generateUUID());
            account.setGoal(accountDetails.getGoal());
            account.setSavingsType(account.getSavingsType());
            account.setToBeWithdrawnAt(LocalDateTime.now());
            account.setTransactionsList(new ArrayList<>());
            account.setUser(user.get());
            account.setWallet(wallet);
            repository.save(account);
            response.setStatusCode(200);
            response.setErrorMessage("Savings account created");
            response.setSavingsAccount(account);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setErrorMessage("Savings account not created");
        }
        return response;
    }

    @Override
    public SavingsAccountDto unsubscribeFromSavingsAccount(Long userId) {
        return null;
    }

    @Override
    public SavingsAccountDto withdrawFromSavingsAccountToWallet(Long userId, Long amount,Long savingsAccountId) {
        User user =userRepository.findById(userId).get();
        Wallet wallet =walletRepository.findById(userId).get();
        SavingsAccount account =repository.findById(savingsAccountId).get();
        TransactionsTabl transactionsTabl =new TransactionsTabl();
        SavingsAccountDto response =new SavingsAccountDto();
        try{
            if(account.
                    getUser().
                    getId().
                    equals(userId)){
                if(account.getAmount()-amount>0){
                    wallet.setAmount(wallet.getAmount()+amount);
                    walletRepository.save(wallet);

                    transactionsTabl.setReceiverId(user.getAccountNumber());
                    transactionsTabl.setSenderId(user.getAccountNumber());
                    transactionsTabl.setAmount(amount);
                    transactionsTabl.setTransactionId(generateUUID());
                    transactionsTabl.setTransactionTime(LocalDateTime.now());
                    transactionsTabl.setTransactionType("WITHDRAWAL FROM SAVINGS TO WALLET! ");
                    transactionsTabl.setUser(user);
                    transactionsRepository.save(transactionsTabl);

                    account.setAmount(account.getAmount()-amount);
                    repository.save(account);

                    response.setStatusCode(200);
                    response.setMessage("transaction completed from your saving account to you wallet");
                }else{
                    response.setStatusCode(404);
                    response.setErrorMessage("amount is higher than the balance!");
                }
            }else{
                response.setStatusCode(404);
                response.setErrorMessage("userId does not match savings account wallet!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
