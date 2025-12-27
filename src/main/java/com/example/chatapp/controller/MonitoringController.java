package com.example.chatapp.controller;

import com.example.chatapp.state.OnlineUserStore;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RestController
@RequestMapping("/api/monitoring")
@RequiredArgsConstructor
public class MonitoringController {
  /**
   * Get all online usernames
   */
  @GetMapping("/ws/online-users")
  public Set<String> onlineUsers() {
    return OnlineUserStore.getOnlineUsers();
  }
}
