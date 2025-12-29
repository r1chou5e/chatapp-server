package com.example.chatapp.controller;

import com.example.chatapp.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessageSendingOperations messagingTemplate;

  /**
   * Send message to a global topic
   */
  @MessageMapping("/send-message")
  @SendTo("/topic/global")
  public ChatMessage sendMessage(ChatMessage message) {
    log.info("[GLOBAL CHAT] {}: {}", message.getSender(), message.getContent());

    return message;
  }

  /*
  * Send message to specific room
  * */
  @MessageMapping("/rooms/{roomId}")
  public void sendRoomMessage(
      @DestinationVariable String roomId,
      ChatMessage message
  ) {
    log.info("[ROOM {}] {}: {}", roomId, message.getSender(), message.getContent());

    messagingTemplate.convertAndSend(
        "/topic/rooms/" + roomId,
        message
    );
  }
}
