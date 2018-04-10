package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class SlotValidationInvalidException extends RuntimeException {

  private static final String MESSAGE = "Slot validation %s does not cover current reservations";

  private final long roomId;
  private final SlotValidationInfo slotValidationInfo;

  public SlotValidationInvalidException(long roomId, SlotValidationInfo slotValidationInfo) {
    this.roomId = roomId;
    this.slotValidationInfo = slotValidationInfo;
  }
}
