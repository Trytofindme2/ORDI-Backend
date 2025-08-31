package com.example.ordi2.DTO;

import java.time.LocalDateTime;
import java.util.UUID;


public class ChatMessageDTO {

    private UUID senderId;
    private UUID receiverId;
    private String textContent;
    private ChatReceipeDTO sendReceipe;
    private LocalDateTime sentAt;

    public ChatMessageDTO() {}

    public ChatMessageDTO(UUID senderId, UUID receiverId, String textContent,
                          ChatReceipeDTO sendReceipe, LocalDateTime sentAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.textContent = textContent;
        this.sendReceipe = sendReceipe;
        this.sentAt = sentAt;
    }

   

	// ✅ Getters & Setters
    public UUID getSenderId() { return senderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }

    public UUID getReceiverId() { return receiverId; }
    public void setReceiverId(UUID receiverId) { this.receiverId = receiverId; }

    public String getTextContent() { return textContent; }
    public void setTextContent(String textContent) { this.textContent = textContent; }

    public ChatReceipeDTO getSendReceipe() { return sendReceipe; }
    public void setSendReceipe(ChatReceipeDTO sendReceipe) { this.sendReceipe = sendReceipe; }

    

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
