package com.example.chatapp.controller;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  /**
   * Send message to a global topic
   */
  @MessageMapping("/send/global")
  public void sendMessage(ChatMessage message) {
    chatService.sendGlobalMessage(message);
  }

  /*
  * Send message to specific room
  * */
  @MessageMapping("/send/rooms/{roomId}")
  public void sendRoomMessage(
      @DestinationVariable String roomId,
      ChatMessage message,
      StompHeaderAccessor accessor
  ) {
    String stompSessionId = accessor.getSessionId();
    chatService.sendRoomMessage(roomId, stompSessionId, message);
  }
}
