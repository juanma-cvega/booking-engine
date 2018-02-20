package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class JoinRequestDeniedEvent implements Event {

  private final long accessRequestId;
  private final long userId;
  private final long clubId;
}
