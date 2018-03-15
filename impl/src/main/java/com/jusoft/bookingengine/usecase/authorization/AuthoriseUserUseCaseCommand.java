package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class AuthoriseUserUseCaseCommand implements Command {

  private final long buildingId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime slotCreationTime;
  private final long clubId;
  private final long userId;
}
