package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportResponseDTO
{
    private UUID id;
    private String reportReason;
    private UUID recipeId;
    private String recipeTitle;
    private UUID reportedById;
    private String reportedByName;
    private UUID postOwnerId;       // new
    private String postOwnerName;   // new
    private LocalDateTime postAt;
    private LocalDateTime reportAt;

    public ReportResponseDTO(){}

    public ReportResponseDTO(
            UUID id,
            String reportReason,
            UUID recipeId,
            String recipeTitle,
            UUID reportedById,
            String reportedByName,
            UUID postOwnerId,        // new
            String postOwnerName,    // new
            LocalDateTime postAt,
            LocalDateTime reportAt
    ) {
        this.id = id;
        this.reportReason = reportReason;
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.reportedById = reportedById;
        this.reportedByName = reportedByName;
        this.postOwnerId = postOwnerId;       // new
        this.postOwnerName = postOwnerName;   // new
        this.postAt = postAt;
        this.reportAt = reportAt;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public UUID getReportedById() {
        return reportedById;
    }

    public void setReportedById(UUID reportedById) {
        this.reportedById = reportedById;
    }

    public String getReportedByName() {
        return reportedByName;
    }

    public void setReportedByName(String reportedByName) {
        this.reportedByName = reportedByName;
    }

    public LocalDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(LocalDateTime postAt) {
        this.postAt = postAt;
    }

    public LocalDateTime getReportAt() {
        return reportAt;
    }

    public void setReportAt(LocalDateTime reportAt) {
        this.reportAt = reportAt;
    }

    public UUID getPostOwnerId() {
        return postOwnerId;
    }

    public void setPostOwnerId(UUID postOwnerId) {
        this.postOwnerId = postOwnerId;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }
}
