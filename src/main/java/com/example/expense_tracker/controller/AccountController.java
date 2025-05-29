package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.AccountDto;
import com.example.expense_tracker.dto.CreateAccountDto;
import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Get all accounts (Mainly used for testing)
    // GET /api/accounts/
    @GetMapping("/")
    public List<Account> getAllAccount() {
        return accountService.getAllAccounts();
    }

    // Get all accounts for a user
    // GET /api/accounts/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Account> getAccountsByUser(@PathVariable UUID userId) {
        return accountService.getAccountsByUserId(userId);
    }

    // Get a specific account by ID
    // GET /api/accounts/{accountId}
    @GetMapping("/{accountId}")
    public Account getAccountById(@PathVariable UUID accountId) {
        return accountService.getAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Create an account for a user
    // POST /api/accounts/user/{userId}
    @PostMapping("/user/{userId}")
    public Account createAccount(
            @PathVariable UUID userId,
            @RequestBody CreateAccountDto dto) {
        return accountService.createAccount(userId, dto.getName(), dto.getInitialBalance());
    }

    // PUT /api/accounts/{accountId}
    @PutMapping("/{accountId}")
    public Account updateAccount(
            @PathVariable UUID accountId,
            @RequestBody AccountDto dto) {
        return accountService.updateAccount(accountId, dto.getName(), dto.getBalance());
    }

    // DELETE /api/accounts/{accountId}
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
    }

}
