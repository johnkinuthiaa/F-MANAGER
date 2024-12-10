package com.slippery.fmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long amount;
    private UUID transactionId;
    private LocalDateTime transactionTime;
    @ManyToOne
    @JsonBackReference
    private User user;

}
