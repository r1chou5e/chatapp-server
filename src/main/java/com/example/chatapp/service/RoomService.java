package com.example.chatapp.service;

import com.example.chatapp.store.room.RoomPresenceStore;
import com.example.chatapp.store.room.RoomStore;
import com.example.chatapp.util.RoomUtil;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomStore roomStore;
  private final RoomPresenceStore roomPresenceStore;

  public String createRoom(String username) {
    String roomId = generateRoomId();
    roomStore.createRoom(roomId, username);
    roomPresenceStore.join(roomId, username);
    return roomId;
  }

  public void joinRoom(String roomId, String username) {
    if (!roomStore.exists(roomId)) {
      throw new IllegalStateException("Room does not exist");
    }
    roomPresenceStore.join(roomId, username);
  }

  public void leaveRoom(String roomId, String username) {
    roomPresenceStore.leave(roomId, username);

    // optional: auto delete empty room
    if (roomPresenceStore.getUsers(roomId).isEmpty()) {
      roomStore.deleteRoom(roomId);
    }
  }

  public void leaveAllRooms(String username) {
    Set<String> joinedRooms = roomPresenceStore.getRoomsByUser(username);
    for (String roomId: joinedRooms) {
      leaveRoom(roomId, username);
    }
  }

  private String generateRoomId() {
    String roomId = RoomUtil.generateRoomId();
    while (roomStore.exists(roomId)) {
      roomId = RoomUtil.generateRoomId();
    }
    return roomId;
  }
}
