package com.example.ordi2.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receipe_id")
    private Receipe receipe;


	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Comments(String content, LocalDateTime createdAt, User user, Receipe receipe) {
		super();
		this.content = content;
		this.createdAt = createdAt;
		this.user = user;
		this.receipe = receipe;
	}


	public UUID getCommentId() {
		return commentId;
	}


	public void setCommentId(UUID commentId) {
		this.commentId = commentId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Receipe getReceipe() {
		return receipe;
	}


	public void setReceipe(Receipe receipe) {
		this.receipe = receipe;
	}

	
}