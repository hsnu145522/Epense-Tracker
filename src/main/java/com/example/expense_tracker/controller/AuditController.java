package com.example.expense_tracker.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense_tracker.entity.Audit;
import com.example.expense_tracker.service.AuditService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/account/{accountId}")
    public List<Audit> getAuditLogsByAccount(@PathVariable UUID accountId) {
        List<Audit> auditList = auditService.getAuditsByAccountId(accountId);
        return auditList;
    }

}
