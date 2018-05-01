package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.time.Clock;
import java.time.ZonedDateTime;

@Data
class Slot {

  private final long id;
  private final long roomId;
  private final long buildingId;
  private final long clubId;
  private final ZonedDateTime creationTime;
  private final OpenDate openDate;
  private final SlotState state;

  Slot(long id, long roomId, long buildingId, long clubId, ZonedDateTime creationTime, OpenDate openDate) {
    this(id, roomId, buildingId, clubId, creationTime, openDate, CreatedSlotState.getInstance());
  }

  Slot(long id, long roomId, long buildingId, long clubId, ZonedDateTime creationTime, OpenDate openDate, SlotState state) {
    Validate.notNull(creationTime);
    Validate.notNull(openDate);
    Validate.notNull(state);
    this.id = id;
    this.roomId = roomId;
    this.buildingId = buildingId;
    this.clubId = clubId;
    this.creationTime = creationTime;
    this.openDate = openDate;
    this.state = state;
  }

  Slot reserve(Clock clock) {
    return from(state.reserve(this, clock));
  }

  Slot preReserve(Clock clock) {
    return from(state.preReserve(this, clock));
  }

  Slot makeAvailable() {
    return from(state.makeAvailable(this));
  }

  boolean isOpen(Clock clock) {
    ZonedDateTime now = ZonedDateTime.now(clock);
    return openDate.getStartTime().isAfter(now) || openDate.getStartTime().isEqual(now);
  }

  private Slot from(SlotState state) {
    return new Slot(id, roomId, buildingId, clubId, creationTime, openDate, state);
  }
}
