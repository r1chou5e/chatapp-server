package com.example.chatapp.util;

import com.example.chatapp.model.ChatMessage;

public final class ChatMessageUtil {

  private ChatMessageUtil() {}

  public static ChatMessage sendFromSystem(String content, String type, String username) {
    ChatMessage msg = new ChatMessage();
    msg.setContent(content);
    msg.setSender("SYSTEM");
    msg.setType(type);
    msg.setUsername(username);
    return msg;
  }
}
