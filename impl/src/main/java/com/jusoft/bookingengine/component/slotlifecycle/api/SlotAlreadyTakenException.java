package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SlotAlreadyTakenException extends RuntimeException {

  private static final String MESSAGE = "Slot starting at %s already taken";
  private final ZonedDateTime reservationDate;

  public SlotAlreadyTakenException(ZonedDateTime reservationDate) {
    super(String.format(MESSAGE, reservationDate));
    this.reservationDate = reservationDate;
  }
}
