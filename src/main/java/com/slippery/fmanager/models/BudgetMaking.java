package com.slippery.fmanager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetMaking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long amount;
    private String category;
    private Long totalAmountToBudgetFor;
    private LocalDateTime createdOn;
    private String budgetMonth;
    @ManyToOne
    @JsonBackReference
    private User user;
}
