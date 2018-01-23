package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class CreateSlotCommand implements Command {

  private final long roomId;
  private final long clubId;
  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;

}
