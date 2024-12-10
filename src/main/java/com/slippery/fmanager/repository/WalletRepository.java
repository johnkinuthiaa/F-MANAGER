package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long > {
}
