package com.example.ordi2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Receipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String difficulty;

    private List<String> ingredients;

    private int preparationTime;

    private int cookingTime;

    private LocalDateTime postAt;

    private List<String> imageUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Receipe(){};

    public Receipe(String title, String description, String difficulty, List<String> ingredients, int preparationTime, int cookingTime, LocalDateTime postAt, User user, List<String> imageUrls) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.postAt = postAt;
        this.user = user;
        this.imageUrls = imageUrls;
    }

    public Receipe(String title, String description, String difficulty, List<String> ingredients, int preparationTime, int cookingTime, List<String> imageUrls, User user) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.imageUrls = imageUrls;
        this.user = user;
    }


    @PrePersist
    public void setPostAtBeforePersist() {
        this.postAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(LocalDateTime postAt) {
        this.postAt = postAt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
