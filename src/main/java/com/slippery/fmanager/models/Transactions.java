package com.slippery.fmanager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionType;
    private UUID from;
    private UUID to;
    private BigDecimal amount;
}
