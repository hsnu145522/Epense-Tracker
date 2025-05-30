package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Audit;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    // Additional query methods can be defined here if needed
    List<Audit> findByAccountId(UUID accountId);

}
