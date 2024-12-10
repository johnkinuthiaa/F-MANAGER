package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.TransactionsTabl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<TransactionsTabl,Long> {
}
