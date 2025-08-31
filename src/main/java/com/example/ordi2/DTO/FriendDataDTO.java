package com.example.ordi2.DTO;

import java.util.Set;

public class FriendDataDTO {
	private String message;
    private Set<UserDTO> friends;
    private Set<UserDTO> friendRequestlist;
    private Set<UserDTO> friendReceivelist;
    
	public FriendDataDTO(String message, Set<UserDTO> friends, Set<UserDTO> friendRequestlist,
			Set<UserDTO> friendReceivelist) {
		super();
		this.message = message;
		this.friends = friends;
		this.friendRequestlist = friendRequestlist;
		this.friendReceivelist = friendReceivelist;
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
	public Set<UserDTO> getFriendRequestlist() {
		return friendRequestlist;
	}
	public void setFriendRequestlist(Set<UserDTO> friendRequestlist) {
		this.friendRequestlist = friendRequestlist;
	}
	public Set<UserDTO> getFriendReceivelist() {
		return friendReceivelist;
	}
	public void setFriendReceivelist(Set<UserDTO> friendReceivelist) {
		this.friendReceivelist = friendReceivelist;
	}
    
    

}
