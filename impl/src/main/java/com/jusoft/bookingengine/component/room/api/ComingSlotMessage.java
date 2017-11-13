package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class ComingSlotMessage implements Message {

  private final long roomId;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;
}
