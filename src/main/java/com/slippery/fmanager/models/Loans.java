package com.slippery.fmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Loans {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private UUID loansAccountUUID;
    private Long amountTaken;
    private LocalDateTime payOn;
    private int payAfterDays;
    private int daysExceeded;
    private Long interest;
    private Long amountToPay;
    @ManyToMany
    private List<TransactionsTabl> transactions;
    @OneToOne
    @JsonBackReference
    private User user;
    @OneToOne
    private Wallet wallet;
}
