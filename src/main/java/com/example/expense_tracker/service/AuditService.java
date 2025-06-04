package com.example.expense_tracker.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Audit;
import com.example.expense_tracker.repository.AccountRepository;
import com.example.expense_tracker.repository.AuditRepository;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Audit> getAuditsByAccountId(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return auditRepository.findByAccountId(account.getId());
    }
}
