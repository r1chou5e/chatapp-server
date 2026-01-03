package com.example.chatapp.store.room;

import java.util.Set;

/**
 * Store for tracking room presence based on WebSocket sessions.
 */
public interface RoomPresenceStore {
  /**
   * A session joins a room.
   *
   * @param roomId    the room identifier
   * @param sessionId the WebSocket session identifier
   */
  void join(String roomId, String sessionId);

  /**
   * A session leaves a room.
   *
   * @param roomId    the room identifier
   * @param sessionId the WebSocket session identifier
   */
  void leave(String roomId, String sessionId);

  /**
   * Get all active sessions currently joined in a room.
   *
   * @param roomId the room identifier
   * @return set of sessionIds in the room
   */
  Set<String> getSessions(String roomId);

  /**
   * Get all rooms that a session has joined.
   *
   * @param sessionId the WebSocket session identifier
   * @return set of roomIds that the session is currently in
   */
  Set<String> getRoomsBySession(String sessionId);
}
