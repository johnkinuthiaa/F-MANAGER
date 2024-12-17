package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.BudgetMaking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<BudgetMaking,Long> {
}
