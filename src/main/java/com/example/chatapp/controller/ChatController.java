package com.example.chatapp.controller;

import com.example.chatapp.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {

  @MessageMapping("/send-message")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(ChatMessage message) {
    log.info("Chat message from {}: {}", message.getSender(), message.getContent());

    return message;
  }
}
