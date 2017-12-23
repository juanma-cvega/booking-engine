package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data
public class BookingCreatedMessage implements InfrastructureMessage {

  private final long bookingId;
  private final long userId;
  private final long slotId;
}
