package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.Expenditures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenditureRepository extends JpaRepository<Expenditures,Long> {
}
