package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.TransactionsTabl;
import com.slippery.fmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<TransactionsTabl,Long> {
    List<TransactionsTabl> findByUser(User user);
}
