package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data
public class OpenNextSlotMessage implements InfrastructureMessage {

  private final long roomId;
}
