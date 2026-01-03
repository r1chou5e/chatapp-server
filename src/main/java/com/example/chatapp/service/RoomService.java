package com.example.chatapp.service;

import com.example.chatapp.store.room.RoomPresenceStore;
import com.example.chatapp.store.room.RoomStore;
import com.example.chatapp.store.user.SessionStore;
import com.example.chatapp.util.RoomUtil;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomStore roomStore;
  private final RoomPresenceStore roomPresenceStore;
  private final SessionStore sessionStore;

  public String createRoom(String clientSessionId) {
    String stompSessionId = sessionStore.getStompSessionIdByClientSessionId(clientSessionId);
    String roomId = generateRoomId();
    roomStore.createRoom(roomId);
    roomPresenceStore.join(roomId, stompSessionId);
    return roomId;
  }

  public void joinRoom(String roomId, String clientSessionId) {
    String stompSessionId = sessionStore.getStompSessionIdByClientSessionId(clientSessionId);
    if (!roomStore.exists(roomId)) {
      throw new IllegalStateException("Room does not exist");
    }
    roomPresenceStore.join(roomId, stompSessionId);
  }

  public void leaveRoom(String roomId, String clientSessionId) {
    String stompSessionId = sessionStore.getStompSessionIdByClientSessionId(clientSessionId);
    roomPresenceStore.leave(roomId, stompSessionId);

    // optional: auto delete empty room
    if (roomPresenceStore.getSessions(roomId).isEmpty()) {
      roomStore.deleteRoom(roomId);
    }
  }

  public void leaveAllRooms(String stompSessionId) {
    Set<String> joinedRooms = roomPresenceStore.getRoomsBySession(stompSessionId);
    for (String roomId : joinedRooms) {
      leaveRoom(roomId, stompSessionId);
    }
  }

  /**
   * Get online users (usernames) in a room.
   */
  public Set<String> getUsers(String roomId) {
    return roomPresenceStore.getSessions(roomId).stream()
        .map(sessionStore::getUsername)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }

  private String generateRoomId() {
    String roomId = RoomUtil.generateRoomId();
    while (roomStore.exists(roomId)) {
      roomId = RoomUtil.generateRoomId();
    }
    return roomId;
  }
}
