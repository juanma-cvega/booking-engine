package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class CheckAuthorizationCommand implements Command {

  @NonNull
  private final Coordinates coordinates;
  private final long clubId;
  private final long userId;
}
