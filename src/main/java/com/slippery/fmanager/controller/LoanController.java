package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.LoanDto;
import com.slippery.fmanager.models.Loans;
import com.slippery.fmanager.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }
    @PostMapping("/new/loans")
    public ResponseEntity<LoanDto> getLoan(@RequestParam Long userId, @RequestBody Loans loansDetails){
        return ResponseEntity.ok(service.getLoan(userId, loansDetails));
    }
}
