package com.jusoft.bookingengine.component.booking;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class Booking {

  private final long id;
  private final long userId;
  @NonNull
  private final ZonedDateTime bookingTime;
  private final long slotId;

  boolean isOwner(Long requestUserId) {
    return Long.compare(requestUserId, userId) == 0;
  }
}
