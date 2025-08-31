package com.example.ordi2.DTO;


import java.util.List;

public class FriendMessageDTO {

    private UserDTO sender;
    private UserDTO receiver;
    private List<ChatMessageDTO> chatmessagelist;

    public FriendMessageDTO() {}

	public FriendMessageDTO(UserDTO sender, UserDTO receiver, List<ChatMessageDTO> chatmessagelist) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.chatmessagelist = chatmessagelist;
	}

	public UserDTO getSender() {
		return sender;
	}

	public void setSender(UserDTO sender) {
		this.sender = sender;
	}

	public UserDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(UserDTO receiver) {
		this.receiver = receiver;
	}

	public List<ChatMessageDTO> getChatmessagelist() {
		return chatmessagelist;
	}

	public void setChatmessagelist(List<ChatMessageDTO> chatmessagelist) {
		this.chatmessagelist = chatmessagelist;
	}
    
    

    
}
