package com.jusoft.slot;

import static com.jusoft.util.TimeUtil.getTimeFrom;

class SlotResourceFactory {

    SlotResource createFrom(Slot slot) {
        return new SlotResource(slot.getSlotId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
    }

}
