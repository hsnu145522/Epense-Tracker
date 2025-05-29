package com.example.expense_tracker.dto;

import java.math.BigDecimal;

public class AccountDto {
    private String name;
    private BigDecimal balance;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
