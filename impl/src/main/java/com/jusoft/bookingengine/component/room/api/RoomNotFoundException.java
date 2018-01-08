package com.jusoft.bookingengine.component.room.api;

import lombok.Getter;

@Getter
public class RoomNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Room %s not found";
  private final long roomId;

  public RoomNotFoundException(long roomId) {
    super(String.format(MESSAGE, roomId));
    this.roomId = roomId;
  }
}
