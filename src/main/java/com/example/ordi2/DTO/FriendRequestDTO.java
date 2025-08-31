package com.example.ordi2.DTO;

import java.util.UUID;

public class FriendRequestDTO {

	private UUID addUserId;
    private UUID receiveUserId;
	public UUID getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(UUID addUserId) {
		this.addUserId = addUserId;
	}
	public UUID getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(UUID receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
    
}
