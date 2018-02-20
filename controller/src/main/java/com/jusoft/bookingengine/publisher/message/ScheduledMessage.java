package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data(staticConstructor = "of")
public class ScheduledMessage implements InfrastructureMessage {

  @NonNull
  private final InfrastructureMessage message;
  @NonNull
  private final ZonedDateTime executionTime;
}
