package com.jusoft.bookingengine.component.room.api;

public class RoomNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Room %s not found";

  public RoomNotFoundException(long roomId) {
    super(String.format(MESSAGE, roomId));
  }
}
