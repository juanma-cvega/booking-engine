package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class PreReservationNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Pre reservation at %s not found in room %s";
  private final long roomId;
  private final ZonedDateTime slotStartTime;

  public PreReservationNotFoundException(long roomId, ZonedDateTime slotStartTime) {
    super(String.format(MESSAGE, slotStartTime, roomId));
    this.roomId = roomId;
    this.slotStartTime = slotStartTime;
  }
}
