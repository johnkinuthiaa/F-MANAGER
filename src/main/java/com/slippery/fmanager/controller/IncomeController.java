package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.IncomeDto;
import com.slippery.fmanager.models.Incomes;
import com.slippery.fmanager.service.IncomesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {
    private final IncomesService service;

    public IncomeController(IncomesService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<IncomeDto> createNewIncome(@RequestBody Incomes incomesDetails,@RequestParam Long userId){
        return ResponseEntity.ok(service.createNewIncome(incomesDetails, userId));
    }
    @GetMapping("/get/income/name")
    public ResponseEntity<IncomeDto> getIncomeByName(@RequestParam Long userId,@RequestParam String name){
        return ResponseEntity.ok(service.getIncomeByName(userId,name));
    }
    @DeleteMapping("/delete/income")
    public ResponseEntity<IncomeDto> deleteAllIncomesForUser(@RequestParam Long userId){
        return ResponseEntity.ok(service.deleteAllIncomesForUser(userId));
    }
    @DeleteMapping("/delete/income/byId")
    public ResponseEntity<IncomeDto> deleteIncomeById(@RequestParam Long userId, @RequestParam Long incomeId){
        return ResponseEntity.ok(service.deleteIncomeById(userId, incomeId));
    }
    @GetMapping("/get/user/Incomes")
    public ResponseEntity<IncomeDto> viewAllIncomes(@RequestParam Long userId){
        return ResponseEntity.ok(service.viewAllIncomes(userId));
    }
}
