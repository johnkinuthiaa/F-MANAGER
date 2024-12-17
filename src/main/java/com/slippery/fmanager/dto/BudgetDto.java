package com.slippery.fmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.fmanager.models.BudgetMaking;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetDto {
    private String message;
    private String errorMessage;
    private int statusCode;
    private BudgetMaking budget;
    private List<BudgetMaking> budgetList;
}
