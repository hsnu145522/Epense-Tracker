package com.example.expense_tracker.repository;

import java.util.UUID;
import com.example.expense_tracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
