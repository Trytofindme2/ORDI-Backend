package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReceipeDTO {
    private UUID id;
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
    private String useremail;
    private boolean friendstatus;
    private boolean followstatus;
    private boolean friendrequeststatus;
    private int reactionCount;
    private int commentCount;

    public ReceipeDTO() {}
    
    

    public ReceipeDTO(UUID id, String title, String description, String difficulty, List<String> ingredients,
			int preparationTime, int cookingTime, LocalDateTime postAt, List<String> imageUrls, UUID userId,
			String name, String userProfileUrl, String useremail) {
		super();
		this.id = id;
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
		this.useremail = useremail;
	}



	public ReceipeDTO(UUID id, String title, String description, String difficulty, List<String> ingredients,
                      int preparationTime, int cookingTime, LocalDateTime postAt, List<String> imageUrls,int reactionCount,int commentCount,
                      UUID userId, String username,String userProfileUrl,String useremail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.postAt = postAt;
        this.imageUrls = imageUrls;
        this.reactionCount = reactionCount;
        this.commentCount = commentCount;
        this.userId = userId;
        this.name = username;
        this.userProfileUrl = userProfileUrl;
        this.useremail = useremail;
        
    }
	
	

    public ReceipeDTO(UUID id, String title, String description, String difficulty, List<String> ingredients,
			int preparationTime, int cookingTime, LocalDateTime postAt, List<String> imageUrls, boolean friendstatus, boolean followstatus,
			boolean friendrequeststatus, int reactionCount, int commentCount, UUID userId,
			String name, String userProfileUrl, String useremail) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.difficulty = difficulty;
		this.ingredients = ingredients;
		this.preparationTime = preparationTime;
		this.cookingTime = cookingTime;
		this.postAt = postAt;
		this.imageUrls = imageUrls;
		this.friendstatus = friendstatus;
		this.followstatus = followstatus;
		this.friendrequeststatus = friendrequeststatus;
		this.reactionCount = reactionCount;
		this.commentCount = commentCount;
		this.userId = userId;
		this.name = name;
		this.userProfileUrl = userProfileUrl;
		this.useremail = useremail;
		
	}



	// Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public LocalDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(LocalDateTime postAt) {
        this.postAt = postAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

	public int getReactionCount() {
		return reactionCount;
	}

	public void setReactionCount(int reactionCount) {
		this.reactionCount = reactionCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}



	public String getUseremail() {
		return useremail;
	}



	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}



	public boolean isFriendstatus() {
		return friendstatus;
	}



	public void setFriendstatus(boolean friendstatus) {
		this.friendstatus = friendstatus;
	}



	public boolean isFollowstatus() {
		return followstatus;
	}



	public void setFollowstatus(boolean followstatus) {
		this.followstatus = followstatus;
	}



	public boolean isFriendrequeststatus() {
		return friendrequeststatus;
	}



	public void setFriendrequeststatus(boolean friendrequeststatus) {
		this.friendrequeststatus = friendrequeststatus;
	}
    
    
    
}
