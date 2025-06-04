package com.example.expense_tracker.dto;

import java.math.BigDecimal;

public class CreateAccountDto {
    private String name;
    private BigDecimal initialBalance;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}
