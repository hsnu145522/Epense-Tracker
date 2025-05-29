package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.TransactionDto;
import com.example.expense_tracker.dto.UpdateTransactionDto;
import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
            @RequestBody TransactionDto dto) {
        return transactionService.createTransaction(
                dto.getCategoryId(),
                dto.getAccountId(),
                dto.getAmount(),
                dto.getDescription());
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
    public Transaction updateTransaction(
            @PathVariable UUID id,
            @RequestBody UpdateTransactionDto dto) {
        return transactionService.updateTransactionAmount(id, dto.getNewAmount());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
