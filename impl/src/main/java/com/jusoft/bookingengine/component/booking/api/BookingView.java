package com.jusoft.bookingengine.component.booking.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class BookingView {

  private final long id;
  private final long userId;
  @NonNull
  private final ZonedDateTime bookingTime;
  private final long slotId;
}
