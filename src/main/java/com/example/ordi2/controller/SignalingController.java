package com.example.ordi2.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class SignalingController {

    private final SimpMessagingTemplate messagingTemplate;

    public SignalingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/signal")
    public void signaling(@Payload Map<String, Object> message) {
        String targetId = (String) message.get("receiver");
        messagingTemplate.convertAndSendToUser(targetId, "/signal", message);
    }

}