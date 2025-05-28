package com.example.expense_tracker.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "category")
public class Category {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("categories")
    private User user;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;

    public Category() {
        this.id = UUID.randomUUID();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public enum CategoryType {
        INCOME,
        EXPENSE
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
