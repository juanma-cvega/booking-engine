package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class CreateJoinRequestCommand implements Command {

  private final long clubId;
  private final long userId;
}
