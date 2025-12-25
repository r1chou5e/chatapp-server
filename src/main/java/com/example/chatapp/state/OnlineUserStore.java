package com.example.chatapp.state;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineUserStore {

  // sessionId -> username
  private static final ConcurrentHashMap<String, String> onlineUsers = new ConcurrentHashMap<>();

  public static void add(String sessionId, String username) {
    onlineUsers.put(sessionId, username);
  }

  public static String remove(String sessionId) {
    return onlineUsers.remove(sessionId);
  }

  public static Set<String> getOnlineUsers() {
    return Set.copyOf(onlineUsers.values());
  }

  public static String getUsername(String sessionId) {
    return onlineUsers.get(sessionId);
  }
}
