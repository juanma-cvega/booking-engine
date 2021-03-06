package com.jusoft.bookingengine.component.room.api;

import lombok.Getter;

@Getter
public class RoomNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -4568266700510340079L;

  private static final String MESSAGE = "Room %s not found";

  private final long roomId;

  public RoomNotFoundException(long roomId) {
    super(String.format(MESSAGE, roomId));
    this.roomId = roomId;
  }
}
