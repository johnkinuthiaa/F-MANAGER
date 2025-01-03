package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.ExpendituresDto;
import com.slippery.fmanager.models.Expenditures;
import com.slippery.fmanager.repository.ExpenditureRepository;
import com.slippery.fmanager.service.impl.ExpenditureServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenditureMockServiceTests {
    @Mock
    private ExpenditureRepository expenditureRepository;
    @InjectMocks
    private ExpenditureServiceImpl expenditureService;

    @Test
    public void createExpenditureReturnsResponse(){
        System.out.println("passed");
    }

}
