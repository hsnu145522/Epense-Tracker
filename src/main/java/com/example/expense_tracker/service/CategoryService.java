package com.example.expense_tracker.service;

import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.entity.Category.CategoryType;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Category createCategory(UUID userId, String name, CategoryType type) {
        if (categoryRepository.findByUserIdAndNameAndType(userId, name, type).isPresent()) {
            throw new RuntimeException("Category with same name of this type already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setUser(user);
        category.setName(name);
        category.setType(type);
        return categoryRepository.save(category);
    }

    public void createDefaultCategoriesForUser(UUID userId) {
        // Default categories
        createCategory(userId, "Salery", CategoryType.INCOME);
        createCategory(userId, "Bonus", CategoryType.INCOME);
        createCategory(userId, "Other Income", CategoryType.INCOME);
        createCategory(userId, "Food", CategoryType.EXPENSE);
        createCategory(userId, "Transportation", CategoryType.EXPENSE);
        createCategory(userId, "Other Expense", CategoryType.EXPENSE);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoriesByUserId(UUID userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(UUID id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(newName);
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}