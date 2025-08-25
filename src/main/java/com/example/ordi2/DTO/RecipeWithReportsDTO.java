package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RecipeWithReportsDTO {

    private UUID recipeId;
    private String title;
    private String description;
    private String difficulty;
    private List<String> ingredients;
    private int preparationTime;
    private int cookingTime;
    private LocalDateTime postAt;
    private List<String> imageUrls;

    private UUID userId;
    private String name;
    private String userProfileUrl;

    private List<ReportInfo> reports;

    // Nested DTO for reports
    public static class ReportInfo {
        private UUID reportId;             // ID of the individual report
        private UUID reportedBy;           // ID of the reporter
        private String reportedByName;     // Reporter name
        private String reporterProfileUrl; // Reporter profile picture
        private String reportReason;       // Reason of report
        private LocalDateTime reportAt;    // Report timestamp

        public ReportInfo() {}

        public ReportInfo(UUID reportId, UUID reportedBy, String reportedByName, String reporterProfileUrl,
                          String reportReason, LocalDateTime reportAt) {
            this.reportId = reportId;
            this.reportedBy = reportedBy;
            this.reportedByName = reportedByName;
            this.reporterProfileUrl = reporterProfileUrl;
            this.reportReason = reportReason;
            this.reportAt = reportAt;
        }

        // Getters & Setters
        public UUID getReportId() { return reportId; }
        public void setReportId(UUID reportId) { this.reportId = reportId; }

        public UUID getReportedBy() { return reportedBy; }
        public void setReportedBy(UUID reportedBy) { this.reportedBy = reportedBy; }

        public String getReportedByName() { return reportedByName; }
        public void setReportedByName(String reportedByName) { this.reportedByName = reportedByName; }

        public String getReporterProfileUrl() { return reporterProfileUrl; }
        public void setReporterProfileUrl(String reporterProfileUrl) { this.reporterProfileUrl = reporterProfileUrl; }

        public String getReportReason() { return reportReason; }
        public void setReportReason(String reportReason) { this.reportReason = reportReason; }

        public LocalDateTime getReportAt() { return reportAt; }
        public void setReportAt(LocalDateTime reportAt) { this.reportAt = reportAt; }
    }

    // Constructors
    public RecipeWithReportsDTO() {}

    public RecipeWithReportsDTO(UUID recipeId, String title, String description, String difficulty,
                                List<String> ingredients, int preparationTime, int cookingTime,
                                LocalDateTime postAt, List<String> imageUrls, UUID userId,
                                String name, String userProfileUrl, List<ReportInfo> reports) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.postAt = postAt;
        this.imageUrls = imageUrls;
        this.userId = userId;
        this.name = name;
        this.userProfileUrl = userProfileUrl;
        this.reports = reports;
    }

    // Getters & Setters
    public UUID getRecipeId() { return recipeId; }
    public void setRecipeId(UUID recipeId) { this.recipeId = recipeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    public int getCookingTime() { return cookingTime; }
    public void setCookingTime(int cookingTime) { this.cookingTime = cookingTime; }

    public LocalDateTime getPostAt() { return postAt; }
    public void setPostAt(LocalDateTime postAt) { this.postAt = postAt; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUserProfileUrl() { return userProfileUrl; }
    public void setUserProfileUrl(String userProfileUrl) { this.userProfileUrl = userProfileUrl; }

    public List<ReportInfo> getReports() { return reports; }
    public void setReports(List<ReportInfo> reports) { this.reports = reports; }
}
