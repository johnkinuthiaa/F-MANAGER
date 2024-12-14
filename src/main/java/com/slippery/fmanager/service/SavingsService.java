package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.SavingsAccountDto;
import com.slippery.fmanager.models.SavingsAccount;

public interface SavingsService {
    SavingsAccountDto createNewSavingsAccount(SavingsAccount accountDetails,Long userId);
    SavingsAccountDto depositToSavingsAccount(Long userId,Long amount);
    SavingsAccountDto unsubscribeFromSavingsAccount(Long userId);
    SavingsAccountDto withdrawFromSavingsAccountToWallet(Long userId,Long amount,Long savingsAccountId);
}
