package com.example.ordi2.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.ordi2.model.ChatMessage;
import com.example.ordi2.model.Message;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.User;
import com.example.ordi2.service.ChatMessageService;
import com.example.ordi2.service.receipeService;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private com.example.ordi2.service.userService userService;

	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private receipeService receipeService;

	@MessageMapping("/private-message")
	public void recMessage(@Payload Message message) throws Exception {
		User sender = userService.findUserByEmail(message.getSenderEmail());
		User receiver = userService.findUserByEmail(message.getReceiverEmail());
		Receipe receipe = receipeService.getReceipeById(message.getReceipeId());
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setSender(sender);
		chatMessage.setReceiver(receiver);
		chatMessage.setTextContent(message.getTextContent());
		chatMessage.setSendReceipe(receipe);
		chatMessage.setSentAt(LocalDateTime.now());
		chatMessageService.save(chatMessage);
		simpMessagingTemplate.convertAndSendToUser(receiver.getEmail(), "/queue/private", message);
		simpMessagingTemplate.convertAndSendToUser(sender.getEmail(), "/queue/private", message);
	}
	
	
}

//@Controller
//public class ChatController {
//
//	@Autowired
//	private SimpMessagingTemplate simpMessagingTemplate;
//	@Autowired
//	private com.example.ordi2.service.userService userService;
//	@Autowired
//	private ChatMessageService chatMessageService;
//
//
//	@MessageMapping("/private-message")
//	public void recMessage(@Payload Message message) {
//	    // Now Jackson can deserialize properly
//		User sender = userService.findUserByEmail(message.getSenderEmail());
//    	User receiver = userService.findUserByEmail(message.getReceiverEmail());
//		ChatMessage chatMessage = new ChatMessage();
//		
//	    chatMessage.setSender(sender);
//	    chatMessage.setReceiver(receiver);
//
//	    chatMessage.setTextContent(message.getTextContent());
//	    chatMessage.setSentAt(LocalDateTime.now());
//
//	    chatMessageService.save(chatMessage);
//	}
//	
//
//}