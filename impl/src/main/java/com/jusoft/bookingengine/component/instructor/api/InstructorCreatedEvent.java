package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class InstructorCreatedEvent implements Event {

  private final long instructorId;
  private final long clubId;
}
