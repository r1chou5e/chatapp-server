package com.example.chatapp.controller;

import com.example.chatapp.response.OnlineUsersResponse;
import com.example.chatapp.store.user.SessionStore;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

  private final SessionStore sessionStore;

  /**
   * Get all online usernames
   */
  @GetMapping("/ws/online-users")
  public ResponseEntity<OnlineUsersResponse> onlineUsers() {
    val onlineUsers = sessionStore.getOnlineUsers();
    return ResponseEntity.ok(
        OnlineUsersResponse.builder().count(onlineUsers.size())
            .users(onlineUsers).build());
  }
}
