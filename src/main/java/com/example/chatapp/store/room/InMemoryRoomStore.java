package com.example.chatapp.store.room;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoomStore implements RoomStore {

  private final Set<String> rooms = ConcurrentHashMap.newKeySet();

  @Override
  public void createRoom(String roomId, String createdBy) {
    if (!rooms.add(roomId)) {
      throw new IllegalStateException("Room already exists");
    }
  }

  @Override
  public void deleteRoom(String roomId) {
    if (!rooms.remove(roomId)) {
      throw new IllegalStateException("Room not exists");
    }
  }

  @Override
  public boolean exists(String roomId) {
    return rooms.contains(roomId);
  }

  @Override
  public Set<String> getRooms() {
    return Set.copyOf(rooms);
  }

  @Override
  public int count() {
    return rooms.size();
  }
}
