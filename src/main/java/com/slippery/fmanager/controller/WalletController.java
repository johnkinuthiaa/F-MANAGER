package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.WalletDto;
import com.slippery.fmanager.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }
    @GetMapping("/get/userId")
    public ResponseEntity<WalletDto> getWalletByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(service.getWalletByUserId(userId));
    }
}
