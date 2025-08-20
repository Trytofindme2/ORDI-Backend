package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SavedPostResponseDTO {
    private UUID savePostId;   // id of SavePosts entity
    private UUID postId;
    private String title;
    private String description;
    private String difficulty;
    private List<String> ingredients;
    private int preparationTime;
    private int cookingTime;
    private List<String> imageUrls;
    private String authorName;
    private String authorProfileUrl;
    private LocalDateTime postAt;
    private LocalDateTime savedAt;

    public SavedPostResponseDTO(UUID savePostId, UUID postId, String title, String description, String difficulty,
                        List<String> ingredients, int preparationTime, int cookingTime, List<String> imageUrls,
                        String authorName, String authorProfileUrl, LocalDateTime postAt, LocalDateTime savedAt) {
        this.savePostId = savePostId;
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.imageUrls = imageUrls;
        this.authorName = authorName;
        this.authorProfileUrl = authorProfileUrl;
        this.postAt = postAt;
        this.savedAt = savedAt;
    }

    public UUID getSavePostId() {
        return savePostId;
    }

    public void setSavePostId(UUID savePostId) {
        this.savePostId = savePostId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorProfileUrl() {
        return authorProfileUrl;
    }

    public void setAuthorProfileUrl(String authorProfileUrl) {
        this.authorProfileUrl = authorProfileUrl;
    }

    public LocalDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(LocalDateTime postAt) {
        this.postAt = postAt;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
