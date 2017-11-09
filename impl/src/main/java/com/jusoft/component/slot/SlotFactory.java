package com.jusoft.component.slot;

import com.jusoft.component.slot.api.CreateSlotCommand;

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
