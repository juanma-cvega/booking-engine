package com.jusoft.component.slot.api;

public class NoSlotsForRoomException extends RuntimeException {

  private static final String MESSAGE = "Room %s has no available slots";

  public NoSlotsForRoomException(long roomId) {
    super(String.format(MESSAGE, roomId));
  }
}
