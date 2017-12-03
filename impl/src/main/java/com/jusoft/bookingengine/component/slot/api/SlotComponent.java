package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.slot.Slot;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

public interface SlotComponent {

  Slot create(CreateSlotCommand createSlotCommand, Clock clock);

  Slot find(long slotId, long roomId);

  boolean isSlotOpen(long slotId, long roomId);

  List<Slot> findOpenSlotsFor(long roomId);

  Optional<Slot> findLastCreatedFor(long roomId);

  Optional<Slot> findSlotInUseOrToStartFor(long roomId);

  int findNumberOfSlotsOpenFor(long roomId);
}
