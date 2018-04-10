package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class ClassConfigOverlappingException extends RuntimeException {

  private static final String MESSAGE = "Room %s already contains slots reserved by the configuration %s";

  private final long roomId;
  private final ClassConfig classConfig;

  public ClassConfigOverlappingException(long roomId, ClassConfig classConfig) {
    super(String.format(MESSAGE, roomId, classConfig));
    this.roomId = roomId;
    this.classConfig = classConfig;
  }
}
