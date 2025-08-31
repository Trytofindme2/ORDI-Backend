package com.example.ordi2.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
	private String senderEmail;
	private String receiverEmail;
	private LocalDateTime datetime;
	private String textContent;
	private UUID receipeId;

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(String senderEmail, String receiverEmail, LocalDateTime datetime, String textContent,UUID receipeId ) {
	       super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.datetime = datetime;
		this.textContent = textContent;
		this.receipeId = receipeId;
	}

	

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public UUID getReceipeId() {
		return receipeId;
	}

	public void setReceipeId(UUID receipeId) {
		this.receipeId = receipeId;
	}

	

}