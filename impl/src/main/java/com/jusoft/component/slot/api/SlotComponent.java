package com.jusoft.component.slot.api;

import com.jusoft.component.slot.Slot;

import java.util.List;
import java.util.Optional;

public interface SlotComponent {

  Slot create(CreateSlotCommand createSlotCommand);

  Slot find(long slotId, long roomId);

  List<Slot> findOpenSlotsFor(long roomId);

  Optional<Slot> findLastCreatedFor(long roomId);

  Optional<Slot> findSlotInUseOrToStartFor(long roomId);
}
