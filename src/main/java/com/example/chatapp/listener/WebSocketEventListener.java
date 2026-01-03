package com.example.chatapp.listener;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.object.session.SessionInfo;
import com.example.chatapp.service.RoomService;
import com.example.chatapp.store.user.SessionStore;
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

  private final SessionStore sessionStore;

  private final RoomService roomService;

  @EventListener
  public void handleConnect(SessionConnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String stompSessionId = accessor.getSessionId();
    String username = accessor.getFirstNativeHeader("username");
    String clientSessionId = accessor.getFirstNativeHeader("clientSessionId");

    if (username == null) {
      log.warn("Session {} connected without username", stompSessionId);
      return;
    }

    sessionStore.add(clientSessionId, stompSessionId, username);

    ChatMessage msg = ChatMesssageUtil.sendFromSystem(
        username + " has connected",
        "JOIN",
        username
    );

    log.info(
        "[CONNECT] WebSocket | clientSessionId={} stompSessionId={} username={}",
        clientSessionId, stompSessionId, username
    );
    messagingTemplate.convertAndSend("/topic/messages", msg);
  }

  @EventListener
  public void handleDisconnect(SessionDisconnectEvent event) {
    String stompSessionId = event.getSessionId();
    SessionInfo info = sessionStore.remove(stompSessionId);
      roomService.leaveAllRooms(stompSessionId);

    if (info != null) {
      ChatMessage msg = ChatMesssageUtil.sendFromSystem(
          info.getUsername() + " has disconnected",
          "LEAVE",
          info.getUsername()
      );

      log.info(
          "[DISCONNECT] WebSocket | stompSessionId={} username={}",
          stompSessionId, info.getUsername()
      );
      messagingTemplate.convertAndSend("/topic/messages", msg);
    }
  }
}
