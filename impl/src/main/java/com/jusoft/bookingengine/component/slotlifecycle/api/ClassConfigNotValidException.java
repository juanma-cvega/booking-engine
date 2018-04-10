package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class ClassConfigNotValidException extends RuntimeException {

  private static final String MESSAGE = "The class timetable %s is not valid for room %s";

  private final long roomId;
  private final ClassConfig classConfig;

  public ClassConfigNotValidException(long roomId, ClassConfig classConfig) {
    super(String.format(MESSAGE, classConfig, roomId));
    this.roomId = roomId;
    this.classConfig = classConfig;
  }
}
