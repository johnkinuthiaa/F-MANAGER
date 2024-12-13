package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.IncomeDto;
import com.slippery.fmanager.models.Incomes;

public interface IncomesService {
    IncomeDto createNewIncome(Incomes incomesDetails,Long userId);
    IncomeDto viewAllIncomes(Long userId);
    IncomeDto deleteIncomeById(Long userId,Long incomeId);
    IncomeDto deleteAllIncomesForUser(Long userId);
    IncomeDto getIncomeByName(Long userId,String name);
    IncomeDto updateIncome(Long userId,Long incomeId);

}
