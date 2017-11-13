package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;

import java.util.function.Supplier;

class SlotFactory {

  private final Supplier<Long> idGenerator;

  SlotFactory(Supplier<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  Slot createFrom(CreateSlotCommand request) {
    return new Slot(idGenerator.get(), request.getRoomId(), request.getStartTime(), request.getEndTime());
  }
}
