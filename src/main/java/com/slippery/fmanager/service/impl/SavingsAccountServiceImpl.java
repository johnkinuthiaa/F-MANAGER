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
            user.get().setLoanLimit(user.get().getLoanLimit()+300);
            userRepository.save(user.get());
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
    public SavingsAccountDto depositToSavingsAccount(Long userId, Long amount) {
        SavingsAccountDto response =new SavingsAccountDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setStatusCode(204);
            response.setErrorMessage("User not found!");
            return response;
        }
        var savingsAccount =repository.findAll().stream()
                .filter(
                        savingAccount -> savingAccount
                                        .getUser()
                                        .getId()
                                        .equals(
                                                user
                                                        .get()
                                                        .getId()))
                .toList();
        System.out.println(savingsAccount);
        if(savingsAccount.isEmpty()){
            response.setStatusCode(204);
            response.setErrorMessage("User"+user.get().getUsername()+"does not have a savings account," +
                    "you can start by creating one!");
            return response;
        }
        if(amount >0){

            savingsAccount.get(0).setAmount(savingsAccount.get(0).getAmount()+amount);
            repository.save(savingsAccount.get(0));
            TransactionsTabl transaction =new TransactionsTabl();
            transaction.setUser(user.get());
            transaction.setTransactionType("a deposit to savings account".toUpperCase());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setAmount(amount);
            transaction.setTransactionId(generateUUID());
            transaction.setSenderId(user.get().getAccountNumber());
            transaction.setReceiverId(savingsAccount.get(0).getSavingAccountNumber());
            transactionsRepository.save(transaction);
            if(user.get().isBlackListed()){
                user.get().setLoanLimit(0L);
            }else{
                user.get().setLoanLimit(user.get().getLoanLimit()+100);
            }

            userRepository.save(user.get());
            response.setMessage(amount+ " successfully deposited to your savings account");
            response.setStatusCode(200);
        }else{
            response.setMessage("amount should be a valid number and not less than 0");
            response.setStatusCode(200);
        }

        return response;
    }

    @Override
    public SavingsAccountDto unsubscribeFromSavingsAccount(Long userId) {
        SavingsAccountDto response =new SavingsAccountDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("user not found");
        }
        var savingsAccount =repository.findAll().stream()
                .filter(account -> account.getUser().getId().equals(userId))
                .toList();
        if(!savingsAccount.isEmpty() && savingsAccount.get(0).getAmount() ==0){
            repository.delete(savingsAccount.get(0));
            response.setStatusCode(200);
            response.setErrorMessage("Savings account for "+user.get().getUsername()+" deleted");
        }else{
            response.setStatusCode(204);
            response.setErrorMessage("Savings account for "+user.get().getUsername()+
                    " does not exist or has some money," +
                    "please withdraw first then delete the account");
        }
        return response;
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
                if(account.getAmount()-amount>=0){
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
