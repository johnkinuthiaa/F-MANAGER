package com.slippery.fmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    private String message;
    private String errorMessage;
    private int statusCode;
    private String transactionType;
    private UUID from;
    private UUID to;
    private BigDecimal amount;
    private User user;
    private List<TransactionsTabl> transactions;
}
