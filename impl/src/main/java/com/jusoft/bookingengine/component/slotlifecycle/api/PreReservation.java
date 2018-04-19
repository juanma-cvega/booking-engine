package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class PreReservation {

  private final long userId;
  @NonNull
  private final ZonedDateTime reservationDate;

}
