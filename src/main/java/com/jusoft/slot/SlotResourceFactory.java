package com.jusoft.slot;

import java.util.List;

import static com.jusoft.util.TimeUtil.getTimeFrom;
import static java.util.stream.Collectors.toList;

class SlotResourceFactory {

    SlotResource createFrom(Slot slot) {
        return new SlotResource(slot.getSlotId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
    }

    SlotResources createFrom(List<Slot> slots) {
        return new SlotResources(slots.stream().map(this::createFrom).collect(toList()));
    }

}
