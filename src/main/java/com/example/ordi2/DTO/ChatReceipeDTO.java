package com.example.ordi2.DTO;

import java.util.List;
import java.util.UUID;
public class ChatReceipeDTO {

    private UUID id;
    private String title;
    private String description;
    private List<String> imageUrls;
    private UUID userId;
    private String name;
    private String userProfileUrl;
    private String useremail;

    public ChatReceipeDTO() {}

    

    public ChatReceipeDTO(UUID id, String title, String description, List<String> imageUrls, UUID userId, String name,
			String userProfileUrl, String useremail) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageUrls = imageUrls;
		this.userId = userId;
		this.name = name;
		this.userProfileUrl = userProfileUrl;
		this.useremail = useremail;
	}



	// ✅ Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUserProfileUrl() { return userProfileUrl; }
    public void setUserProfileUrl(String userProfileUrl) { this.userProfileUrl = userProfileUrl; }

    public String getUseremail() { return useremail; }
    public void setUseremail(String useremail) { this.useremail = useremail; }



	public List<String> getImageUrls() {
		return imageUrls;
	}



	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
    
}
