package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MakeSlotAvailableUseCase {

    private final SlotManagerComponent slotManagerComponent;

    public void makeSlotAvailable(long slotId) {
        slotManagerComponent.makeAvailable(slotId);
    }
}
