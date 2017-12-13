package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotView;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class SlotFactory {

  private final Supplier<Long> idGenerator;

  SlotFactory(Supplier<Long> idGenerator) {
    this.idGenerator = idGenerator;
  }

  Slot createFrom(CreateSlotCommand request, Clock clock) {
    return new Slot(idGenerator.get(),
      request.getRoomId(),
      ZonedDateTime.now(clock),
      request.getStartTime(),
      request.getEndTime());
  }

  SlotView createFrom(Slot slot) {
    return new SlotView(slot.getId(),
      slot.getRoomId(),
      slot.getCreationTime(),
      slot.getStartDate(),
      slot.getEndDate());
  }

  public List<SlotView> createFrom(List<Slot> slots) {
    return slots.stream().map(this::createFrom).collect(Collectors.toList());
  }
}
