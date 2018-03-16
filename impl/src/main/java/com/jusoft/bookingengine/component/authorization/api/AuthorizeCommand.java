package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class AuthorizeCommand implements Command {

  private final long userId;
  private final long roomId;
  private final long buildingId;
  private final long clubId;
  @NonNull
  private final ZonedDateTime slotCreationTime;
}
