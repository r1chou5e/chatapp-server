package com.example.chatapp.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
  private String roomId;
  private String sender;
  private String content;
  private String type;
  private String username;
  private Instant timestamp;
}
