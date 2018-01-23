package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class SlotCreatedMessage implements InfrastructureMessage {

  private final long slotId;
  private final long roomId;
  private final long clubId;
  @NonNull
  private final ZonedDateTime startDate;
  @NonNull
  private final ZonedDateTime endDate;
}
