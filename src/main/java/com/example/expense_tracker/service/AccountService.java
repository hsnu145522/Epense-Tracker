package com.example.expense_tracker.service;

import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Category.CategoryType;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.AccountRepository;
import com.example.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUserId(UUID userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account createAccount(UUID userId, String name, BigDecimal initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setName(name);
        account.setBalance(initialBalance != null ? initialBalance : BigDecimal.ZERO);

        return accountRepository.save(account);
    }
    public void createDefaultAccountsForUser(UUID userId) {
        // Default Accounts
        createAccount(userId, "Cash", BigDecimal.ZERO);
    }

    public Optional<Account> getAccountById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    public Account updateAccount(UUID accountId, String newName, BigDecimal newBalance) {
        return accountRepository.findById(accountId)
            .map(account -> {
                if (newName != null) account.setName(newName);
                if (newBalance != null) account.setBalance(newBalance);
                return accountRepository.save(account);
            }).orElseThrow(() -> new RuntimeException("Account not found"));
    }
    
    public void deleteAccount(UUID accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(accountId);
    }
}
