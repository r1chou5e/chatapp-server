package com.example.chatapp.store.user;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryOnlineUserStore implements OnlineUserStore {

  // sessionId -> username
  private static final ConcurrentHashMap<String, String> onlineUsers = new ConcurrentHashMap<>();

  @Override
  public void add(String sessionId, String username) {
    onlineUsers.put(sessionId, username);
  }

  @Override
  public String remove(String sessionId) {
    return onlineUsers.remove(sessionId);
  }

  @Override
  public Set<String> getOnlineUsers() {
    return Set.copyOf(onlineUsers.values());
  }

  @Override
  public int count() {
    return onlineUsers.size();
  }

  @Override
  public String getUsername(String sessionId) {
    return onlineUsers.get(sessionId);
  }
}
