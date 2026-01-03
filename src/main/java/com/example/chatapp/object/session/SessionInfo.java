package com.example.chatapp.object.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionInfo {
  String stompSessionId;
  String clientSessionId;
  String username;
}
