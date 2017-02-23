package com.jusoft.component.slot;

public interface SlotComponent {

    SlotResource create(CreateSlotRequest createSlotRequest);

    SlotResource find(Long slotId, Long roomId);

    SlotResources getSlotsFor(Long roomId);
}
