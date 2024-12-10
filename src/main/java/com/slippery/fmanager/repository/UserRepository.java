package com.slippery.fmanager.repository;

import com.slippery.fmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    Optional<User> findUserByAccountNumber(UUID accountNumber);

}