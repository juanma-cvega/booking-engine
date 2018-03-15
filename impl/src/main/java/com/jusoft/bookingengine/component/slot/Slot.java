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
  private final long buildingId;
  private final long clubId;
  @NonNull
  private final ZonedDateTime creationTime;
  @NonNull
  private final OpenDate openDate;
  @NonNull
  private final State state;

  public Slot reserve(Clock clock) {
    return from(state.getSlotState().reserve(this, clock));
  }

  public boolean isAvailable(Clock clock) {
    return state.getSlotState().isAvailable(this, clock);
  }

  public Slot makeAvailable() {
    return from(state.getSlotState().makeAvailable(this));
  }

  public Slot reserveForAuctionWinner() {
    return from(state.getSlotState().reserveForAuctionWinner(this));
  }

  private Slot from(State state) {
    return new Slot(id, roomId, buildingId, clubId, creationTime, openDate, state);
  }
}
