package com.example.ordi2.DTO;

import java.util.Set;

public class FriendListDTO {
	private String message;
    private Set<UserDTO> friends;
	public FriendListDTO(String message, Set<UserDTO> friends) {
		super();
		this.message = message;
		this.friends = friends;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Set<UserDTO> getFriends() {
		return friends;
	}
	public void setFriends(Set<UserDTO> friends) {
		this.friends = friends;
	}
    
}
