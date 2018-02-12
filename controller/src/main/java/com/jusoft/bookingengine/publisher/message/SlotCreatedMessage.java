package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;
import lombok.NonNull;

@Data
public class SlotCreatedMessage implements InfrastructureMessage {

  private final long slotId;
  private final long roomId;
  private final SlotState state;
  @NonNull
  private final OpenDate openDate;
}
