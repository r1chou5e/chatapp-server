package com.example.chatapp.util;

import java.security.SecureRandom;

public class RoomUtil {
  private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";;
  private static final int LENGTH = 6;
  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateRoomId() {
    StringBuilder sb = new StringBuilder(LENGTH);
    for (int i = 0; i < LENGTH; i++) {
      sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
    }
    return sb.toString();
  }
}
