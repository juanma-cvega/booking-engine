package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Data;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class PreReservation {

  private final long userId;
  private final ZonedDateTime reservationDate;

}
