package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loans,Long> {
}
