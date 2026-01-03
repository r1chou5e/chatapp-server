package com.example.chatapp.store.room;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoomPresenceStore implements RoomPresenceStore{

  // roomId -> set of stompSessionId
  private final ConcurrentHashMap<String, Set<String>> rooms = new ConcurrentHashMap<>();

  @Override
  public void join(String roomId, String sessionId) {
    rooms
        .computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet())
        .add(sessionId);
  }

  @Override
  public void leave(String roomId, String sessionId) {
    Set<String> sessions = rooms.get(roomId);
    if (sessions != null) {
      sessions.remove(sessionId);
      if (sessions.isEmpty()) {
        rooms.remove(roomId);
      }
    }
  }

  @Override
  public Set<String> getSessions(String roomId) {
    return Set.copyOf(rooms.getOrDefault(roomId, Set.of()));
  }

  @Override
  public Set<String> getRoomsBySession(String sessionId) {
    return rooms.entrySet()
        .stream()
        .filter(entry -> entry.getValue().contains(sessionId))
        .map(Entry::getKey)
        .collect(Collectors.toSet());
  }

  @Override
  public boolean isInRoom(String roomId, String sessionId) {
    return rooms.get(roomId).contains(sessionId);
  }
}
