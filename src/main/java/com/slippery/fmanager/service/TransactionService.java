package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.TransactionDto;
import com.slippery.fmanager.models.TransactionsTabl;

public interface TransactionService {
    TransactionDto sendMoney(TransactionsTabl transactions);
    TransactionDto getAllTransactionsRecordsByUser(Long userId);
    TransactionDto getAllTransactionsRecords();
}
