package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class Coordinates {

  private final long buildingId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime slotCreationTime;
}
