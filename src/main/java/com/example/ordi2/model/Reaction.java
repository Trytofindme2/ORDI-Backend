package com.example.ordi2.model;

import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receipe_id")
    private Receipe receipe;
    
    private int reactioncount;

    
	public Reaction() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reaction(com.example.ordi2.model.User user, Receipe receipe, int reactioncount) {
		super();
		this.user = user;
		this.receipe = receipe;
		this.reactioncount = reactioncount;
	}


	public UUID getReactionId() {
		return reactionId;
	}


	public void setReactionId(UUID reactionId) {
		this.reactionId = reactionId;
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


	public int getReactioncount() {
		return reactioncount;
	}


	public void setReactioncount(int reactioncount) {
		this.reactioncount = reactioncount;
	}
	
	
    
    

}
