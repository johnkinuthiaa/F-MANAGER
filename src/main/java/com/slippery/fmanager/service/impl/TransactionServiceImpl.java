package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.TransactionDto;

import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.repository.TransactionsRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.TransactionService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;

    public TransactionServiceImpl(TransactionsRepository transactionsRepository, UserRepository userRepository, ResourceLoader resourceLoader) {
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
    }

    private UUID generateTransactionId(){
        return UUID.randomUUID();
    }

    @Override
    public TransactionDto sendMoney(TransactionsTabl transactions, Long senderId) {
        TransactionDto response =new TransactionDto();
        Optional<User> user =userRepository.findById(senderId);
        try{
            if(user.isPresent()) {
                TransactionsTabl transactions1 = new TransactionsTabl();
                transactions1.setAmount(transactions.getAmount());
                transactions1.setSenderId(transactions.getSenderId());
                transactions.setReceiverId(transactions.getReceiverId());
                transactions1.setTransactionType("SEND MONEY");
                transactions1.setUser(transactions.getUser());
                transactions1.setTransactionId(generateTransactionId());
                transactions1.setTransactionTime(LocalDateTime.now());
                transactionsRepository.save(transactions1);
                response.setMessage(
                        transactions1.getTransactionId()+" confirmed. ksh"
                        +transactions1.getAmount()
                                +"sent to"
                                +transactions1.getReceiverId() +
                                " at "+transactions1.getTransactionTime()
                );
                response.setStatusCode(200);
            }


        } catch (Exception e) {
           response.setErrorMessage(e.getMessage().trim());
           response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public TransactionDto getAllTransactionsRecords(Long userId) {
        return null;
    }
}
