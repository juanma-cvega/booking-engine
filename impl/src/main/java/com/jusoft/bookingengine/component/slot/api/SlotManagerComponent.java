package com.jusoft.bookingengine.component.slot.api;

import java.util.List;
import java.util.Optional;

public interface SlotManagerComponent {

  SlotView create(CreateSlotCommand createSlotCommand);

  SlotView find(long slotId);

  boolean isSlotOpen(long slotId);

  List<SlotView> findOpenSlotsFor(long roomId);

  Optional<SlotView> findLastCreatedFor(long roomId);

  Optional<SlotView> findSlotInUseOrToStartFor(long roomId);

  int findNumberOfSlotsOpenFor(long roomId);

  void reserveSlot(long slotId, SlotUser slotUser);

  void preReserveSlot(long slotId, SlotUser slotUser);

  void makeAvailable(long slotId);

}
