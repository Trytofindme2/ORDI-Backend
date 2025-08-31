package com.example.ordi2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordi2.model.ChatMessage;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.ChatMessageRepo;
import com.example.ordi2.repo.userRepo;

@Service
public class ChatMessageService {
	
	@Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private userRepo userRepository;

    public ChatMessage save(ChatMessage message) {
        // Fetch managed User entities
        User sender = userRepository.findUserByEmail(message.getSender().getEmail());
        User receiver = userRepository.findUserByEmail(message.getReceiver().getEmail());
        message.setSender(sender);
        message.setReceiver(receiver);
        return chatMessageRepo.save(message);
    }

    public List<ChatMessage> getChatHistory(String userEmail) {
        return chatMessageRepo.findBySenderEmailOrReceiverEmailOrderBySentAtAsc(userEmail, userEmail);
    }

    public List<ChatMessage> getMessagesBetweenUsers(User user1, User user2) {
        return chatMessageRepo.findChatMessagesBetweenUsers(user1, user2);
    }

	public ChatMessage getMessageById(UUID messageId) {
		// TODO Auto-generated method stub
		return chatMessageRepo.findBymessageId(messageId);
	}

}
