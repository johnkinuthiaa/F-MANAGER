package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.TransactionDto;

import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }
    @PostMapping("/send")
    public ResponseEntity<TransactionDto> sendMoney(@RequestBody TransactionsTabl transactions){
        return ResponseEntity.ok(service.sendMoney(transactions));
    }
    @GetMapping("/transactions/by/userId")
    public ResponseEntity<TransactionDto> getAllTransactionsRecords(@RequestParam Long userId){
        return ResponseEntity.ok(service.getAllTransactionsRecords(userId));
    }

    @GetMapping("/all/transactions")
    public ResponseEntity<TransactionDto> getAllTransactionsRecords(){
        return ResponseEntity.ok(service.getAllTransactionsRecords());
    }

}
