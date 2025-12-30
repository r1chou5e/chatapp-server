package com.example.chatapp.store.room;

import java.util.Set;

public interface RoomPresenceStore {
  /*
  * User join the room
  * */
  void join(String roomId, String username);

  /*
   * User leave the room
   * */
  void leave(String roomId, String username);

  /*
  * Get online users of the room
  * */
  Set<String> getUsers(String roomId);

  /*
   * Get rooms by users
   * */
  Set<String> getRoomsByUser(String username);
}
