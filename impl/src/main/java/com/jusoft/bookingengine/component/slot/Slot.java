package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.SlotState.State;
import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;
import lombok.NonNull;

import java.time.Clock;
import java.time.ZonedDateTime;

@Data
class Slot {

  private final long id;
  private final long roomId;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final OpenDate openDate;
  @NonNull
  private final State state;

  public Slot reserve(Clock clock) {
    return new Slot(id, roomId, creationTime, openDate, state.getSlotState().reserve(this, clock));
  }

  public boolean isAvailable(Clock clock) {
    return state.getSlotState().isAvailable(this, clock);
  }

  public Slot makeAvailable() {
    return new Slot(id, roomId, creationTime, openDate, state.getSlotState().makeAvailable(this));
  }

  public Slot reserveForAuctionWinner() {
    return new Slot(id, roomId, creationTime, openDate, state.getSlotState().reserveForAuctionWinner(this));
  }
}
