package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data
public class ClubCreatedEvent implements Event {

  private final long clubId;

}
