package com.example.chatapp.service;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.store.room.RoomPresenceStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

  private final SimpMessageSendingOperations messagingTemplate;
  private final RoomPresenceStore roomPresenceStore;

  public void sendGlobalMessage(ChatMessage message) {
    log.info("[GLOBAL CHAT] {}: {}", message.getSender(), message.getContent());

    messagingTemplate.convertAndSend(
        "/topic/global",
        message
    );
  }

  public void sendRoomMessage(String roomId, String stompSessionId, ChatMessage message) {
    // Verify if the user is part of the room before sending the message
    if (!roomPresenceStore.isInRoom(roomId, stompSessionId)) {
      throw new IllegalStateException("Session has not join the room");
    }

    log.info("[ROOM {}] {}: {}", roomId, message.getSender(), message.getContent());

    // Logic to send the message to the room (omitted for brevity)
    messagingTemplate.convertAndSend(
        "/topic/rooms/" + roomId,
        message
    );
  }
}
