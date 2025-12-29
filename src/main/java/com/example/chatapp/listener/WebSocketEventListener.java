package com.example.chatapp.listener;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.store.room.RoomPresenceStore;
import com.example.chatapp.store.user.OnlineUserStore;
import com.example.chatapp.util.ChatMesssageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
  private final SimpMessageSendingOperations messagingTemplate;

  private final OnlineUserStore onlineUserStore;

  private final RoomPresenceStore roomPresenceStore;

  @EventListener
  public void handleConnect(SessionConnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = accessor.getSessionId();
    String username = accessor.getFirstNativeHeader("username");

    if (username != null) {
      onlineUserStore.add(sessionId, username);

      ChatMessage msg = ChatMesssageUtil.sendFromSystem(
          username + " has connected",
          "JOIN",
          username
      );

      log.info("[{}] {}", msg.getSender(), msg.getContent());
      messagingTemplate.convertAndSend("/topic/messages", msg);
    }
  }

  @EventListener
  public void handleDisconnect(SessionDisconnectEvent event) {
    String sessionId = event.getSessionId();
    String username = onlineUserStore.remove(sessionId);

    if (username != null) {
      ChatMessage msg = ChatMesssageUtil.sendFromSystem(
          username + " has disconnected",
          "LEAVE",
          username
      );

      log.info("[{}] {}", msg.getSender(), msg.getContent());
      messagingTemplate.convertAndSend("/topic/messages", msg);
    }
  }
}
