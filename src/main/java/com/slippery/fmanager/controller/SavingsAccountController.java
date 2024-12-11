package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.SavingsAccountDto;
import com.slippery.fmanager.models.SavingsAccount;
import com.slippery.fmanager.service.SavingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savingsAccount")
public class SavingsAccountController {
    private final SavingsService service;
    /*
    * SavingsAccountDto createNewSavingsAccount(SavingsAccount accountDetails,Long userId);
    SavingsAccountDto unsubscribeFromSavingsAccount(Long userId);
    SavingsAccountDto withdrawFromSavingsAccountToWallet(Long userId,Long amount,Long savingsAccountId);
    * */
    public SavingsAccountController(SavingsService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<SavingsAccountDto> createNewSavingsAccount(
            @RequestBody SavingsAccount accountDetails,
            @RequestParam Long userId
    ){
        return ResponseEntity.ok(service.createNewSavingsAccount(accountDetails, userId));
    }

}
