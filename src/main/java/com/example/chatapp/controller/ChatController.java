package com.example.chatapp.controller;

import com.example.chatapp.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  @MessageMapping("/send-message")
  @SendTo("/topic/messages")
  public ChatMessage sendMessage(ChatMessage message) {
    System.out.println("Received message: " + message.getContent());

    return message;
  }

  @MessageMapping("/leave")
  @SendTo("/topic/messages")
  public ChatMessage leave(String username) {
    ChatMessage newMessage = new ChatMessage();
    newMessage.setContent(username + " has left the chat!");
    newMessage.setSender("SYSTEM");
    newMessage.setType("LEAVE");
    newMessage.setUsername(username);
    return newMessage;
  }

  @MessageMapping("/join")
  @SendTo("/topic/messages")
  public ChatMessage join(String username) {
    ChatMessage newMessage = new ChatMessage();
    newMessage.setContent(username + " has joined the chat!");
    newMessage.setSender("SYSTEM");
    newMessage.setType("JOIN");
    newMessage.setUsername(username);
    return newMessage;
  }
}
