package com.jusoft.component.room;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@Builder
public class UpcomingSlot {

  @NonNull
  private final Long roomId;
  @NonNull
  private final ZonedDateTime creationTime;
}
