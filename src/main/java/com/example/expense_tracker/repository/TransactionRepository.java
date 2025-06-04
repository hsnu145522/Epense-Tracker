package com.example.expense_tracker.repository;

import java.util.List;
import java.util.UUID;
import com.example.expense_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccountId(UUID accountId);
    List<Transaction> findByCategoryId(UUID categoryId);
}
