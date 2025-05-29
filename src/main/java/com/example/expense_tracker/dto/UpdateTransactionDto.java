package com.example.expense_tracker.dto;

import java.math.BigDecimal;

public class UpdateTransactionDto {
    private BigDecimal newAmount;

    // Getter/setter
    public BigDecimal getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
    }
}
