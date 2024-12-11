package com.slippery.fmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.fmanager.models.SavingsAccount;
import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingsAccountDto {
    private String message;
    private int statusCode;
    private String errorMessage;
    private Long id;
    private UUID savingAccountNumber;
    private Long amount;
    private String savingsType;
    private Long goal;
    private LocalDateTime createdOn;
    private LocalDateTime toBrWithdrawnAt;
    private User user;
    private List<TransactionsTabl> transactionsList;
    private SavingsAccount savingsAccount;
}
