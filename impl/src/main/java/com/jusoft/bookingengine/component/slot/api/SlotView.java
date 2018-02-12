package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class SlotView {

  private final long id;
  private final long roomId;
  @NonNull
  private final SlotState state;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final OpenDate openDate;
}
