package com.example.chatapp.store.room;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoomPresenceStore implements RoomPresenceStore{

  private final ConcurrentHashMap<String, Set<String>> rooms = new ConcurrentHashMap<>();

  @Override
  public void join(String roomId, String username) {
    Set<String> users = rooms.computeIfAbsent(
        roomId,
        k -> ConcurrentHashMap.newKeySet()
    );

    boolean added = users.add(username);
    if (!added) {
      throw new IllegalStateException(
          String.format(
              "User '%s' already joined room '%s'",
              username,
              roomId
          )
      );
    }
  }

  @Override
  public void leave(String roomId, String username) {
    Set<String> users = rooms.get(roomId);
    if (users != null) {
      users.remove(username);
      if (users.isEmpty()) {
        rooms.remove(roomId);
      }
    }
  }

  @Override
  public Set<String> getUsers(String roomId) {
    return rooms.getOrDefault(roomId, Set.of());
  }
}
