package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.ExpendituresDto;
import com.slippery.fmanager.models.Expenditures;
import com.slippery.fmanager.service.ExpenditureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenditures")

public class ExpenditureController {
    private final ExpenditureService service;

    public ExpenditureController(ExpenditureService service) {
        this.service = service;
    }
    @PostMapping("/new/expenditure")
    public ResponseEntity<ExpendituresDto> createNewExpenditure(@RequestBody Expenditures expenditureDetails,@RequestParam Long userId){
        return ResponseEntity.ok(service.createNewExpenditure(expenditureDetails,userId));
    }
    @GetMapping("/get/expenditure")
    public ResponseEntity<ExpendituresDto> getAllExpendituresByUser(@RequestParam Long userId){
        return ResponseEntity.ok(service.getAllExpendituresByUser(userId));
    }
    @DeleteMapping("/delete/expenditure/id")
    public ResponseEntity<ExpendituresDto> deleteExpenditureById(@RequestParam Long userId, @RequestParam Long expenditureId){
        return ResponseEntity.ok(service.deleteExpenditureById(userId,expenditureId));
    }
}
