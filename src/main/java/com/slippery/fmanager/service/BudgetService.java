package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.BudgetDto;
import com.slippery.fmanager.models.BudgetMaking;

public interface BudgetService {
    BudgetDto createNewBudget(BudgetMaking budgetDetails,Long userId);
    BudgetDto updateBudget(BudgetMaking budgetDetails,Long userId,Long budgetId);
    BudgetDto deleteBudgetById(Long userId,Long budgetId);
    BudgetDto getBudgetById(Long userId,Long budgetId);
    BudgetDto getAllBudget(Long userId);
    BudgetDto findBudgetWithName(String name,Long userId);

}
