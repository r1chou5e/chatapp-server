package com.example.chatapp.store.room;

import java.util.Set;

public interface RoomStore {
  /*
  * Create new room by username
  * */
  void createRoom(String roomId);

  /*
   * Delete the room
   * */
  void deleteRoom(String roomId);

  /*
   * Check room exists
   * */
  boolean exists(String roomId);

  /*
   * Get the rooms
   * */
  Set<String> getRooms();

  /*
  * Count the rooms exists
  * */
  int count();
}
