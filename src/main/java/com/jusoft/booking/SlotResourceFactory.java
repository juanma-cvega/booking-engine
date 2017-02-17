package com.jusoft.booking;

import com.jusoft.slot.SlotResource;

import static com.jusoft.util.TimeUtil.getLocalDateTimeFrom;
import static com.jusoft.util.TimeUtil.getTimeFrom;

class SlotResourceFactory {

    SlotResource createFrom(Slot slot) {
        return new SlotResource(slot.getSlotId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
    }

    Slot createFrom(SlotResource slot) {
        return new Slot(slot.getSlotId(), slot.getRoomId(), getLocalDateTimeFrom(slot.getStartDate()), getLocalDateTimeFrom(slot.getEndDate()));
    }
}
