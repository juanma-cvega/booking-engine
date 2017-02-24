package com.jusoft.component.slot;

import java.util.List;

public interface SlotComponent {

    Slot create(CreateSlotCommand createSlotCommand);

    Slot find(long slotId, long roomId);

    List<Slot> getSlotsFor(long roomId);
}
