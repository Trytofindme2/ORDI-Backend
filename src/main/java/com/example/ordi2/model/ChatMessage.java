package com.example.ordi2.model;

import java.time.LocalDateTime;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID messageId;

	@ManyToOne
	@JsonIgnoreProperties({ "friends", "followers", "following", "sentRequests", "receivedRequests" })
	private User sender;

	@ManyToOne
	@JsonIgnoreProperties({ "friends", "followers", "following", "sentRequests", "receivedRequests" })
	private User receiver;

	private String textContent;
	
	private boolean isRead = false;
	private LocalDateTime sentAt;

	@ManyToOne
	@JoinColumn(name = "receipe_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Receipe sendReceipe;


	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ChatMessage(User sender, User receiver, String textContent, boolean isRead, LocalDateTime sentAt,
			com.example.ordi2.model.Receipe receipe) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.textContent = textContent;
		this.isRead = isRead;
		this.sentAt = sentAt;
		this.sendReceipe = receipe;
	}



	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}



	public UUID getMessageId() {
		return messageId;
	}



	public void setMessageId(UUID messageId) {
		this.messageId = messageId;
	}



	public Receipe getSendReceipe() {
		return sendReceipe;
	}



	public void setSendReceipe(Receipe receipe) {
		this.sendReceipe = receipe;
	}

	

}