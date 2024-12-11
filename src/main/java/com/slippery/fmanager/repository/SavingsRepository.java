package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<SavingsAccount,Long> {
}
