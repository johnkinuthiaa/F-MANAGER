package com.slippery.fmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavingsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID savingAccountNumber;
    private Long amount;
    private String savingsType;
    private Long goal;
    private LocalDateTime createdOn;
    private LocalDateTime toBeWithdrawnAt;
    @OneToOne
    @JsonBackReference
    private User user;
    @OneToOne
    private Wallet wallet;
    @ManyToMany

    private List<TransactionsTabl> transactionsList;

}
