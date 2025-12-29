package com.example.chatapp.controller;

import com.example.chatapp.request.CreateRoomRequest;
import com.example.chatapp.request.JoinRoomRequest;
import com.example.chatapp.request.LeaveRoomRequest;
import com.example.chatapp.response.RoomPresenceResponse;
import com.example.chatapp.response.RoomsResponse;
import com.example.chatapp.service.RoomService;
import com.example.chatapp.store.room.RoomPresenceStore;
import com.example.chatapp.store.room.RoomStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  private final RoomStore roomStore;

  private final RoomPresenceStore roomPresenceStore;

  @PostMapping()
  public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest body) {
    String roomId = roomService.createRoom(body.getUsername());
    log.info("[SYSTEM] {} has created room {}", body.getUsername(), roomId);
    return ResponseEntity.ok(
        String.format("Room %s has been created by user %s", roomId, body.getUsername()));
  }

  @PostMapping("/{roomId}/join")
  public ResponseEntity<?> joinRoom(@PathVariable String roomId,
      @RequestBody JoinRoomRequest body) {
    roomService.joinRoom(roomId, body.getUsername());
    log.info("[SYSTEM] {} has joined room {}", body.getUsername(), roomId);
    return ResponseEntity.ok(
        String.format("User %s has joined the room %s", body.getUsername(), roomId));
  }

  @PostMapping("/{roomId}/leave")
  public ResponseEntity<?> leaveRoom(
      @PathVariable String roomId,
      @RequestBody LeaveRoomRequest body
  ) {
    roomService.leaveRoom(roomId, body.getUsername());
    log.info("[SYSTEM] {} has left room {}", body.getUsername(), roomId);
    return ResponseEntity.ok(
        String.format("User %s has left the room %s", body.getUsername(), roomId)
    );
  }

  @GetMapping
  public ResponseEntity<RoomsResponse> getRooms() {
    return ResponseEntity.ok(
        RoomsResponse.builder().rooms(roomStore.getRooms()).count(roomStore.count()).build());
  }

  @GetMapping("/{roomId}/online-users")
  public ResponseEntity<RoomPresenceResponse> getRooms(@PathVariable String roomId) {
    return ResponseEntity.ok(
        RoomPresenceResponse.builder().onlineUsers(roomPresenceStore.getUsers(roomId)).build());
  }
}

