package com.slippery.fmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Expenditures {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionType;
    private LocalDateTime spentOn;
    private String category;
    private Long amount;
    private Long totalAmountSpent;
    private float percentageIncrease;
    @ManyToOne
    @JsonBackReference
    private User user;
}
