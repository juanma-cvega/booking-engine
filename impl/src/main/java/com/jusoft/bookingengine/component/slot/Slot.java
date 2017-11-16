package com.jusoft.bookingengine.component.slot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Slot {

  private final long id;
  private final long roomId;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final ZonedDateTime startDate;
  @NonNull
  private final ZonedDateTime endDate;

  public boolean isOpen(ZonedDateTime requestTime) {
    return startDate.isAfter(requestTime);
  }
}
