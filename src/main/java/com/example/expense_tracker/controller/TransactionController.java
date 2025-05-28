package com.example.expense_tracker.controller;

import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTranscations() {
        return transactionService.getAllTranscations();
    }

    @GetMapping("/{id}")
    public Transaction getTranscationsById(@PathVariable UUID id) {
        return transactionService.getTransactionsById(id);
    }

    @PostMapping
    public Transaction createTransaction(
            @RequestParam UUID categoryId,
            @RequestParam UUID accountId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return transactionService.createTransaction(categoryId, accountId, amount,
                description);
    }

    @GetMapping("/categories/{categoryId}")
    public List<Transaction> getCategoryTransactions(@PathVariable UUID categoryId) {
        return transactionService.getCategoryTransactions(categoryId);
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getAccountTransactions(@PathVariable UUID accountId) {
        return transactionService.getAccountTransactions(accountId);
    }

    @PutMapping("/{id}")
    public Transaction updateAmount(
            @PathVariable UUID id,
            @RequestParam BigDecimal newAmount) {
        return transactionService.updateTransactionAmount(id, newAmount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
