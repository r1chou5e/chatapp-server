package com.example.chatapp.controller;

import com.example.chatapp.request.CreateRoomRequest;
import com.example.chatapp.request.JoinRoomRequest;
import com.example.chatapp.request.LeaveRoomRequest;
import com.example.chatapp.response.RoomPresenceResponse;
import com.example.chatapp.response.RoomsResponse;
import com.example.chatapp.service.RoomService;
import com.example.chatapp.store.room.RoomStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

  @PostMapping()
  public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest body) {
    String roomId = roomService.createRoom(body.getClientSessionId());
    log.info(
        "[CREATE_ROOM] ROOM | roomId={} clientSessionId={}",
        roomId, body.getClientSessionId()
    );
    return ResponseEntity.ok(
        String.format("Room %s has been created by session %s", roomId, body.getClientSessionId()));
  }

  @PostMapping("/{roomId}/join")
  public ResponseEntity<?> joinRoom(@PathVariable String roomId,
      @RequestBody JoinRoomRequest body) {
    roomService.joinRoom(roomId, body.getClientSessionId());
    log.info(
        "[JOIN_ROOM] ROOM | roomId={} clientSessionId={}",
        roomId, body.getClientSessionId()
    );
    return ResponseEntity.ok(
        String.format("Session %s has joined the room %s", body.getClientSessionId(), roomId));
  }

  @PostMapping("/{roomId}/leave")
  public ResponseEntity<?> leaveRoom(
      @PathVariable String roomId,
      @RequestBody LeaveRoomRequest body
  ) {
    roomService.leaveRoom(roomId, body.getClientSessionId());
    log.info(
        "[LEAVE_ROOM] ROOM | roomId={} clientSessionId={}",
        roomId, body.getClientSessionId()
    );
    return ResponseEntity.ok(
        String.format("Session %s has left the room %s", body.getClientSessionId(), roomId)
    );
  }

  @GetMapping
  public ResponseEntity<RoomsResponse> getRooms() {
    return ResponseEntity.ok(
        RoomsResponse.builder().rooms(roomStore.getRooms()).count(roomStore.count()).build());
  }

  @GetMapping("/{roomId}/online-users")
  public ResponseEntity<RoomPresenceResponse> getRooms(@PathVariable String roomId) {
    val onlineUsers = roomService.getUsers(roomId);
    return ResponseEntity.ok(
        RoomPresenceResponse.builder().onlineUsers(onlineUsers).build());
  }
}

