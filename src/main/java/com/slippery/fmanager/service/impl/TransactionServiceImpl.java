package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.TransactionDto;

import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.TransactionsRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.repository.WalletRepository;
import com.slippery.fmanager.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionsRepository transactionsRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;


    public TransactionServiceImpl(TransactionsRepository transactionsRepository, UserRepository userRepository, WalletRepository walletRepository) {
        this.transactionsRepository = transactionsRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    private UUID generateTransactionId(){
        return UUID.randomUUID();
    }

    @Override
    public TransactionDto sendMoney(TransactionsTabl transactions) {
        TransactionDto response =new TransactionDto();

        Optional<User> sender =userRepository.findUserByAccountNumber(transactions.getSenderId());
        Optional<User>  receiver =userRepository.findUserByAccountNumber(transactions.getReceiverId());

        try{
            if(sender.isPresent() && receiver.isPresent()) {
                Optional<Wallet> senderWallet =walletRepository.findById(sender.get().getId());
                Optional<Wallet> receiverWallet =walletRepository.findById(receiver.get().getId());
                if(senderWallet.get().getAmount() - transactions.getAmount() >0){
//                    for the sender
                    TransactionsTabl transactions1 = new TransactionsTabl();

                    transactions1.setAmount(transactions.getAmount());
                    transactions1.setSenderId(sender.get().getAccountNumber());
                    transactions1.setReceiverId(receiver.get().getAccountNumber());
                    transactions1.setTransactionType("SEND MONEY");
                    transactions1.setUser(sender.get());
                    transactions1.setTransactionId(generateTransactionId());
                    transactions1.setTransactionTime(LocalDateTime.now());
                    transactionsRepository.save(transactions1);

                    //                    for the receiver
                    TransactionsTabl transactions2 = new TransactionsTabl();
                    transactions2.setAmount(transactions.getAmount());
                    transactions2.setSenderId(sender.get().getAccountNumber());
                    transactions2.setReceiverId(receiver.get().getAccountNumber());
                    transactions2.setTransactionType("RECEIVE MONEY");
                    transactions2.setUser(receiver.get());
                    transactions2.setTransactionId(generateTransactionId());
                    transactions2.setTransactionTime(LocalDateTime.now());
                    transactionsRepository.save(transactions2);

                    senderWallet.get().setAmount(senderWallet.get().getAmount()-transactions1.getAmount());
                    receiverWallet.get().setAmount(receiverWallet.get().getAmount() + transactions1.getAmount());
                    walletRepository.save(senderWallet.get());
                    walletRepository.save(receiverWallet.get());
//                   increase the loan limit for the sender by 100 everytime he has a transaction
                    if(sender.get().isBlackListed()){
                        sender.get().setLoanLimit(0L);
                    }else{
                        sender.get().setLoanLimit(sender.get().getLoanLimit()+100);
                    }
                    userRepository.save(sender.get());

                    response.setMessage(
                            transactions1.getTransactionId().toString().substring(0,7)+" confirmed. ksh"
                                    +transactions1.getAmount()
                                    +"sent to "
                                    +receiver.get().getUsername() +
                                    " on "+transactions1.getTransactionTime().toString().substring(0,10)+" at "+
                                    transactions1.getTransactionTime().toString().substring(11,18)
                                    +" hrs your balance is Kshs "
                                    +senderWallet.get().getAmount()
                    );
                    response.setStatusCode(200);
                }else{
                    response.setErrorMessage("Transaction was not complete because your account has insufficient funds");
                    response.setStatusCode(500);
                }


            }else{
                response.setErrorMessage("Transaction was not complete");
                response.setStatusCode(500);
            }


        } catch (Exception e) {
           response.setErrorMessage(e.getMessage().trim());
           response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public TransactionDto getAllTransactionsRecordsByUser(Long userId) {
        TransactionDto response =new TransactionDto();
        Optional <User> user =userRepository.findById(userId);

        if(user.isPresent()){;
            List<TransactionsTabl> transactions =transactionsRepository.findByUser(user.get());
            response.setMessage("All transactions by "+user.get().getUsername());
            response.setTransactions(transactions);
        }else{
            response.setErrorMessage("USER WITH ID"+userId+" not found");
        }
        return response;
    }

    @Override
    public TransactionDto getAllTransactionsRecords() {
        TransactionDto response =new TransactionDto();
        response.setTransactions(transactionsRepository.findAll());
        response.setMessage("All transactions");
        return response;
    }
}
