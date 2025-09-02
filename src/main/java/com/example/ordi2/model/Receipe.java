package com.example.ordi2.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;



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

    @ElementCollection
    private List<String> imageUrls;

    private String videoUrl;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "receipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "receipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavePosts> savePosts = new ArrayList<>();
    
    @OneToMany(mappedBy = "receipe", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "receipe", cascade = CascadeType.ALL)
    private List<Reaction>  reactions = new ArrayList<>();


    @OneToMany(mappedBy = "sendReceipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();


    public Receipe(){};

    

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




    



	public Receipe(String title, String description, String difficulty, List<String> ingredients, int preparationTime,
			int cookingTime, LocalDateTime postAt, List<String> imageUrls, String videoUrl, User user,
			List<Report> reports, List<SavePosts> savePosts, List<Comments> comments, List<Reaction> reactions) {
		super();
		this.title = title;
		this.description = description;
		this.difficulty = difficulty;
		this.ingredients = ingredients;
		this.preparationTime = preparationTime;
		this.cookingTime = cookingTime;
		this.postAt = postAt;
		this.imageUrls = imageUrls;
		this.videoUrl = videoUrl;
		this.user = user;
		this.reports = reports;
		this.savePosts = savePosts;
		this.comments = comments;
		this.reactions = reactions;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }



	public List<SavePosts> getSavePosts() {
		return savePosts;
	}



	public void setSavePosts(List<SavePosts> savePosts) {
		this.savePosts = savePosts;
	}



	public List<Comments> getComments() {
		return comments;
	}



	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}



	public List<Reaction> getReactions() {
		return reactions;
	}



	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}




    
    
}
