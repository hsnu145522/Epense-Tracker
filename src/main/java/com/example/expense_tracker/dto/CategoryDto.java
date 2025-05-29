package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.Category.CategoryType;

public class CategoryDto {
    private String name;
    private CategoryType type;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }
}
