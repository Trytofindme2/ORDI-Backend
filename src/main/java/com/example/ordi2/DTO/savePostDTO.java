package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class savePostDTO
{
    private UUID postId;
    private UUID userId;
    private LocalDateTime savedAt;

    public savePostDTO() {
    }

    public savePostDTO(UUID postId, UUID userId, LocalDateTime savedAt) {
        this.postId = postId;
        this.userId = userId;
        this.savedAt = savedAt;
    }

    public savePostDTO(LocalDateTime savedAt, UUID userId, UUID postId) {
        this.savedAt = savedAt;
        this.userId = userId;
        this.postId = postId;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
