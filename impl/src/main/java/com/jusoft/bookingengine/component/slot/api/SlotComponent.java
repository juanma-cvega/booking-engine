package com.jusoft.bookingengine.component.slot.api;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

public interface SlotComponent {

  SlotView create(CreateSlotCommand createSlotCommand, Clock clock);

  SlotView find(long slotId, long roomId);

  boolean isSlotOpen(long slotId, long roomId);

  List<SlotView> findOpenSlotsFor(long roomId);

  Optional<SlotView> findLastCreatedFor(long roomId);

  Optional<SlotView> findSlotInUseOrToStartFor(long roomId);

  int findNumberOfSlotsOpenFor(long roomId);
}
