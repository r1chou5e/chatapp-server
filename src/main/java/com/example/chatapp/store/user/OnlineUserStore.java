package com.example.chatapp.store.user;

import java.util.Set;

public interface OnlineUserStore {
  /**
   * Mark a user as online (bound to a WebSocket session)
   */
  void add(String sessionId, String username);

  /**
   * Remove user when session is disconnected
   */
  String remove(String sessionId);

  /**
   * Get all online usernames (unique)
   */
  Set<String> getOnlineUsers();

  /**
   * Get current online user count
   */
  int count();


  /**
   * Get username by sessionId
   */
  String getUsername(String sessionId);
}
