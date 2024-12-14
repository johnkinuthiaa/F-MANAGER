package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.LoanDto;
import com.slippery.fmanager.models.Loans;
import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.LoanRepository;
import com.slippery.fmanager.repository.TransactionsRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.repository.WalletRepository;
import com.slippery.fmanager.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository repository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionsRepository transactionsRepository;

    public LoanServiceImpl(LoanRepository repository, UserRepository userRepository, WalletRepository walletRepository, TransactionsRepository transactionsRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public LoanDto getLoan(Long userId, Loans loansDetails) {
        LoanDto response =new LoanDto();

        Optional<User> user =userRepository.findById(userId);
        Optional<Wallet> wallet =walletRepository.findById(userId);
        List<Loans> existing =repository.findAll().stream()
                .filter(loans -> loans.getUser().getId().equals(userId))
                .toList();
        if(!existing.isEmpty()){
            response.setErrorMessage("user already has an existing loan of Ksh"
                    +existing.get(0).getAmountToPay()+
                    " which is to be paid on "+existing.get(0).getPayOn().getDayOfMonth()+" "
                    +existing.get(0).getPayOn().getMonth()+" "
                    +existing.get(0).getPayOn().getYear()
                    +" please pay the amount before requesting for another loan kindly!"
            );
            response.setStatusCode(204);
            return response;
        }
        if(user.isEmpty() || wallet.isEmpty()){
            response.setErrorMessage("user does not exist!");
            response.setStatusCode(204);
            return response;
        }
        if(user.get().isBlackListed()){
            response.setErrorMessage("user is blackListed for failing to repay a loan taken");
            response.setStatusCode(204);
            return response;
        }
        if (user.get().getLoanLimit() -loansDetails.getAmountTaken()>=0 ){
            Loans loan =new Loans();
            loan.setAmountTaken(loansDetails.getAmountTaken());
            loan.setAmountToPay(calculateAmountToPay(loansDetails.getAmountTaken()));
            loan.setPayOn(LocalDateTime.now().plusDays(loansDetails.getPayAfterDays()));
            loan.setPayAfterDays(loansDetails.getPayAfterDays());
            loan.setUser(user.get());
            loan.setWallet(wallet.get());
            loan.setLoansAccountUUID(generateId());
            repository.save(loan);

            if(LocalDateTime.now().isAfter(LocalDateTime.now().plusDays(loan.getPayAfterDays()))){
                user.get().setLoanLimit(0L);
                user.get().setBlackListed(true);
                userRepository.save(user.get());
            }

            Wallet wallet1 =wallet.get();
            wallet1.setAmount(wallet1.getAmount()+loan.getAmountTaken());
            walletRepository.save(wallet1);

            TransactionsTabl transaction =new TransactionsTabl();
            transaction.setTransactionId(generateId());
            transaction.setReceiverId(user.get().getAccountNumber());
            transaction.setSenderId(loan.getLoansAccountUUID());
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setUser(user.get());
            transaction.setAmount(loan.getAmountTaken());
            transaction.setTransactionType("Loan taken from loans account".toUpperCase());
            transactionsRepository.save(transaction);
            user.get().setLoanLimit((long) (user.get().getLoanLimit()+loan.getAmountTaken()*0.2));
            userRepository.save(user.get());

            response.setMessage("Dear,"+user.get().getUsername()+" you have been issued a loan of amount Ksh. "
                    +loan.getAmountTaken()+"to be repaid on "
                    +loan.getPayOn().getDayOfMonth()
                    +" "
                    +loan.getPayOn().getMonth()+" "
                    +loan.getPayOn().getYear()
                            +" failure to which you will attract a penalty of being barred taking a loan later"
                    +" and you will also have to pay a fee. The amount will be repaid as KSH."+loan.getAmountToPay()
                    );
            response.setStatusCode(200);

        }else{
            response.setErrorMessage("YOU CANNOT GET A LOAN MORE THAN YOUR LOAN LIMIT!" +
                    " YOUR LIMIT IS KSH "+user.get().getLoanLimit()+"." +
                    " PLEASE TRY A LOWER AMOUNT! .");
            response.setStatusCode(200);
            return response;
        }
        return response;
    }

    private UUID generateId() {
        return UUID.randomUUID();
    }

    private Long calculateAmountToPay(Long amountTaken) {
        double response =0.00;
        if(amountTaken<100){
            response =amountTaken+ (amountTaken*0.2);
        } else if (amountTaken<200) {
            response =amountTaken+ (amountTaken*0.3);
        }else if (amountTaken<500) {
            response =amountTaken+ (amountTaken*0.4);
        }
        else if (amountTaken<800) {
            response =amountTaken+ (amountTaken*0.5);
        }
        else if (amountTaken<1000) {
            response =amountTaken+ (amountTaken*0.6);
        }
        else if (amountTaken>1000) {
            response =amountTaken+ (amountTaken*0.7);
        }
        return (long) response;
    }
}
