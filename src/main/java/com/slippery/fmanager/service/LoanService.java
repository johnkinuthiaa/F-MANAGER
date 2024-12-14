package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.LoanDto;
import com.slippery.fmanager.models.Loans;

public interface LoanService {
    LoanDto getLoan(Long userId, Loans loansDetails);
}
