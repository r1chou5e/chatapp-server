package com.example.chatapp.controller;

import com.example.chatapp.response.OnlineUsersResponse;
import com.example.chatapp.store.user.OnlineUserStore;
import lombok.RequiredArgsConstructor;
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

  private final OnlineUserStore onlineUserStore;

  /**
   * Get all online usernames
   */
  @GetMapping("/ws/online-users")
  public ResponseEntity<OnlineUsersResponse> onlineUsers() {
    return ResponseEntity.ok(
        OnlineUsersResponse.builder().count(onlineUserStore.count())
            .users(onlineUserStore.getOnlineUsers()).build());
  }
}
