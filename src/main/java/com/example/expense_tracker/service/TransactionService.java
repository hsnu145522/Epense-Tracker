package com.example.expense_tracker.service;

import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.entity.Category.CategoryType;
import com.example.expense_tracker.repository.AccountRepository;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Transaction> getAllTranscations() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionsById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction createTransaction(UUID categoryId, UUID accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create and save transaction
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setCategory(category);
        tx.setDescription(description);
        transactionRepository.save(tx);

        // Update account balance
        CategoryType categoryType = category.getType();
        BigDecimal newBalance;
        if (categoryType == CategoryType.INCOME) {
            newBalance = account.getBalance().add(amount);
        } else {
            newBalance = account.getBalance().subtract(amount);

        }
        account.setBalance(newBalance);
        accountRepository.save(account);

        return tx;
    }

    public List<Transaction> getCategoryTransactions(UUID categoryId) {
        return transactionRepository.findByCategoryId(categoryId);
    }

    public List<Transaction> getAccountTransactions(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction updateTransactionAmount(UUID transactionId, BigDecimal newAmount) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        BigDecimal oldAmount = transaction.getAmount();
        Account account = transaction.getAccount();
        Category category = transaction.getCategory();

        // Revert old transaction effect
        if (category.getType() == Category.CategoryType.INCOME) {
            account.setBalance(account.getBalance().subtract(oldAmount));
            account.setBalance(account.getBalance().add(newAmount));
        } else {
            account.setBalance(account.getBalance().add(oldAmount));
            account.setBalance(account.getBalance().subtract(newAmount));
        }

        transaction.setAmount(newAmount);
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return transaction;
    }

    public void deleteTransaction(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Account account = transaction.getAccount();
        Category category = transaction.getCategory();
        BigDecimal amount = transaction.getAmount();

        // Roll back transaction effect
        if (category.getType() == Category.CategoryType.INCOME) {
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }

        accountRepository.save(account);
        transactionRepository.delete(transaction);
    }
}
