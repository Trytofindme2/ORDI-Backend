package com.example.ordi2.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FriendRequestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID friendrequestuserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonBackReference("sentRequests")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    @JsonBackReference("receivedRequests")
    private User receiver;

    private LocalDate requestdate;
    private String status;

    public FriendRequestUser() {
        // Default constructor
    }

	public FriendRequestUser(User sender, User receiver, LocalDate requestdate, String status) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.requestdate = requestdate;
		this.status = status;
	}

	public UUID getFriendrequestuserId() {
		return friendrequestuserId;
	}

	public void setFriendrequestuserId(UUID friendrequestuserId) {
		this.friendrequestuserId = friendrequestuserId;
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

	public LocalDate getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(LocalDate requestdate) {
		this.requestdate = requestdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

   
}
