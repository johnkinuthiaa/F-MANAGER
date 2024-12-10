package com.slippery.fmanager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TransactionsTabl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionType;
    private UUID senderId;
    private UUID receiverId;
    private BigDecimal amount;
    private UUID transactionId;
    private LocalDateTime transactionTime;
    @ManyToOne
    private User user;

}
