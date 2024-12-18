package com.slippery.fmanager.controller;

import com.slippery.fmanager.dto.BudgetDto;
import com.slippery.fmanager.models.BudgetMaking;
import com.slippery.fmanager.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budget")
@CrossOrigin
public class BudgetController {
    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetDto> createNewBudget(@RequestBody BudgetMaking budgetDetails, @RequestParam Long userId){
        return ResponseEntity.ok(service.createNewBudget(budgetDetails, userId));
    }
    @PutMapping("/update")
    public ResponseEntity<BudgetDto> updateBudget(@RequestBody BudgetMaking budgetDetails, @RequestParam Long userId,@RequestParam Long budgetId){
        return ResponseEntity.ok(service.updateBudget(budgetDetails, userId, budgetId));
    }
    @DeleteMapping("/delete/by/id")
    public ResponseEntity<BudgetDto> deleteBudgetById(@RequestParam Long userId,@RequestParam Long budgetId){
        return ResponseEntity.ok(service.deleteBudgetById(userId, budgetId));
    }
    @GetMapping("/get/id")
    public ResponseEntity<BudgetDto> getBudgetById(@RequestParam Long userId,@RequestParam Long budgetId){
        return ResponseEntity.ok(service.getBudgetById(userId, budgetId));
    }
    @GetMapping("/get/all/user")
    public ResponseEntity<BudgetDto> getAllBudget(@RequestParam Long userId){
        return ResponseEntity.ok(service.getAllBudget(userId));
    }
    @GetMapping("/get/name")
    public ResponseEntity<BudgetDto> findBudgetWithName(@RequestParam String name,@RequestParam Long userId){
        return ResponseEntity.ok(service.findBudgetWithName(name, userId));
    }

}
