package com.example.expense_tracker.controller;

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
    // POST /api/accounts/user/{userId}?name={name}&initialBalance={initialBalance}
    @PostMapping("/user/{userId}")
    public Account createAccount(
            @PathVariable UUID userId,
            @RequestParam String name,
            @RequestParam(required = false) BigDecimal initialBalance) {
        return accountService.createAccount(userId, name, initialBalance);
    }

    // PUT /api/accounts/{accountId}?name={name}&balance={balance}
    @PutMapping("/{accountId}")
    public Account updateAccount(
            @PathVariable UUID accountId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal balance) {
        return accountService.updateAccount(accountId, name, balance);
    }

    // DELETE /api/accounts/{accountId}
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
    }

}
