package com.example.chatapp.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
  private String roomId;
  private String sender;
  private String content;
  private String type;
  private String username;
  private Instant timestamp;
}
