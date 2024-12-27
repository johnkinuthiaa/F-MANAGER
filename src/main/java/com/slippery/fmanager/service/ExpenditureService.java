package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.ExpendituresDto;
import com.slippery.fmanager.models.Expenditures;

public interface ExpenditureService {
    ExpendituresDto createNewExpenditure(Expenditures expenditureDetails,Long userId);
    ExpendituresDto getAllExpendituresByUser(Long userId);
    ExpendituresDto deleteExpenditureById(Long userId,Long expenditureId);
}
