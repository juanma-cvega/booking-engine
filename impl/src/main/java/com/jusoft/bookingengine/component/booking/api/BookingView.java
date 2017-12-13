package com.jusoft.bookingengine.component.booking.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class BookingView {

  private final long id;
  private final long userId;
  @NonNull
  private final ZonedDateTime bookingTime;
  private final long slotId;
  private final long roomId;
}
