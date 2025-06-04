package com.example.expense_tracker.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDto {
    private UUID categoryId;
    private UUID accountId;
    private BigDecimal amount;
    private String description;

    // Getters and setters
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
    public UUID getAccountId() { return accountId; }
    public void setAccountId(UUID accountId) { this.accountId = accountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}