package com.example.expense_tracker.controller;

import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Category.CategoryType;
import com.example.expense_tracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/user/{userId}")
    public Category createCategory(@PathVariable UUID userId, @RequestParam String name,
            @RequestParam CategoryType type) {
        return categoryService.createCategory(userId, name, type);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/user/{userId}")
    public List<Category> getCategoriesByUser(@PathVariable UUID userId) {
        return categoryService.getCategoriesByUserId(userId);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestParam String name) {
        return categoryService.updateCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}
