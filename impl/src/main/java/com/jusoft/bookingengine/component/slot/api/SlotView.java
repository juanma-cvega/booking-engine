package com.jusoft.bookingengine.component.slot.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class SlotView {

  private final long id;
  private final long roomId;
  private final long clubId;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final ZonedDateTime startDate;
  @NonNull
  private final ZonedDateTime endDate;
}
