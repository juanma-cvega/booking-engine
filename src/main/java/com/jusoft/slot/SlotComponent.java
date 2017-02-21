package com.jusoft.slot;

import java.util.List;

public interface SlotComponent {

    SlotResource create(CreateSlotRequest createSlotRequest);

    SlotResource find(Long slotId, Long roomId);

    List<SlotResource> getSlotsFor(Long roomId);
}
