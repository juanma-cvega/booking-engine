package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data
public class JoinRequestCreatedEvent implements Event {

  private final long joinRequestId;
  private final long clubId;
}
