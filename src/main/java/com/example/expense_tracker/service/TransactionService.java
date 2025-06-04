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
    private AccountService accountService;

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
        accountService.updateAccount(account.getId(), account.getName(), newBalance);

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
        BigDecimal updatedBalance = account.getBalance();
        if (category.getType() == Category.CategoryType.INCOME) {
            updatedBalance = updatedBalance.subtract(oldAmount).add(newAmount);
        } else {
            updatedBalance = updatedBalance.subtract(newAmount).add(oldAmount);
        }

        transaction.setAmount(newAmount);
        transactionRepository.save(transaction);
        accountService.updateAccount(account.getId(), account.getName(), updatedBalance);

        return transaction;
    }

    public void deleteTransaction(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Account account = transaction.getAccount();
        Category category = transaction.getCategory();
        BigDecimal amount = transaction.getAmount();

        // Roll back transaction effect
        BigDecimal updatedBalance = account.getBalance();
        if (category.getType() == Category.CategoryType.INCOME) {
            updatedBalance = updatedBalance.subtract(amount);
        } else {
            updatedBalance = updatedBalance.add(amount);
        }

        accountService.updateAccount(account.getId(), account.getName(), updatedBalance);
        transactionRepository.delete(transaction);
    }
}
