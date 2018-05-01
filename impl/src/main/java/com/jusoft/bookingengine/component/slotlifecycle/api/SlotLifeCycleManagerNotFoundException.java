package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class SlotLifeCycleManagerNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8984436501593033852L;

  private static final String MESSAGE = "Slot life cycle manager for room %s not found";

  private final long roomId;

  public SlotLifeCycleManagerNotFoundException(long roomId) {
    super(String.format(MESSAGE, roomId));
    this.roomId = roomId;
  }
}
