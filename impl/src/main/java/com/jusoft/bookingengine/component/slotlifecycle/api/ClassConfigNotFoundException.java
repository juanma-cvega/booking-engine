package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class ClassConfigNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Class %s not found in room %s";

  private final long classId;
  private final long roomId;

  public ClassConfigNotFoundException(long classId, long roomId) {
    super(String.format(MESSAGE, classId, roomId));
    this.classId = classId;
    this.roomId = roomId;
  }
}
