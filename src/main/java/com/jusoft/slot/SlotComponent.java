package com.jusoft.slot;

import java.util.List;

public interface SlotComponent {

    void create(CreateSlotRequest createSlotRequest);

    SlotResource find(Long slotId, Long roomId);

    List<SlotResource> getSlotsFor(Long roomId);
}
