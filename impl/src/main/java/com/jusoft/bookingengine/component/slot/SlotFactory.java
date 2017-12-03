package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

class SlotFactory {

  private final Supplier<Long> idGenerator;

  SlotFactory(Supplier<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  Slot createFrom(CreateSlotCommand request, Clock clock) {
    return new Slot(idGenerator.get(), request.getRoomId(), ZonedDateTime.now(clock), request.getStartTime(), request.getEndTime());
  }
}
