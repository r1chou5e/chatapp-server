package com.example.chatapp.store.room;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoomStore implements RoomStore {

  private final Set<String> rooms = ConcurrentHashMap.newKeySet();

  @Override
  public void createRoom(String roomId) {
    rooms.add(roomId);
  }

  @Override
  public void deleteRoom(String roomId) {
    rooms.remove(roomId);
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
