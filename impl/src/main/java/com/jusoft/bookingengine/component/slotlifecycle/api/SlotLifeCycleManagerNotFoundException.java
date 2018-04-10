package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class SlotLifeCycleManagerNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Slot life cycle manager %s not found";

  private final long roomId;

  public SlotLifeCycleManagerNotFoundException(long roomId) {
    super(String.format(MESSAGE, roomId));
    this.roomId = roomId;
  }
}
