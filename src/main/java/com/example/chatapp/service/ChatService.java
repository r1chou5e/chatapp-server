package com.example.chatapp.service;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.object.session.SessionInfo;
import com.example.chatapp.store.room.RoomPresenceStore;
import com.example.chatapp.store.user.SessionStore;
import java.time.Instant;
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
  private final SessionStore sessionStore;

  public void sendGlobalMessage(ChatMessage message) {
    log.info("[GLOBAL CHAT] {}: {}", message.getSender(), message.getContent());

    messagingTemplate.convertAndSend(
        "/topic/global",
        message
    );
  }

  public void sendRoomMessage(String roomId, String stompSessionId, ChatMessage rawMessage) {
    // Verify if the user is part of the room before sending the message
    if (!roomPresenceStore.isInRoom(roomId, stompSessionId)) {
      throw new IllegalStateException("Session has not join the room");
    }

    // Enrich the message with sender info
    SessionInfo info = sessionStore.getSessionInfoByStompSessionId(stompSessionId);

    ChatMessage message = ChatMessage.builder()
        .roomId(roomId)
        .sender(info.getUsername())
        .content(rawMessage.getContent())
        .timestamp(Instant.now())
        .build();

    // Send the message to the room (omitted for brevity)
    log.info("[ROOM {}] {}: {}", roomId, message.getSender(), message.getContent());

    messagingTemplate.convertAndSend(
        "/topic/rooms/" + roomId,
        message
    );
  }
}
