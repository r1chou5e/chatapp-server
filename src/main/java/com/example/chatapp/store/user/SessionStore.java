package com.example.chatapp.store.user;

import com.example.chatapp.object.session.SessionInfo;
import java.util.Set;

public interface SessionStore {
  /**
   * Add user session
   */
  void add(String clientSessionId, String stompSessionId, String username);

  /**
   * Remove user when session is disconnected
   */
  SessionInfo remove(String stompSessionId);

  /**
   * Get all online usernames (unique)
   */
  Set<String> getOnlineUsers();

  /**
   * Get stomp session id by client session id
   */
  String getStompSessionIdByClientSessionId(String clientSessionId);

  /**
   * Get sessions by username
   */
  Set<String> getSessionsByUsername(String username);


  /**
   * Get username by sessionId
   */
  String getUsername(String sessionId);
}
