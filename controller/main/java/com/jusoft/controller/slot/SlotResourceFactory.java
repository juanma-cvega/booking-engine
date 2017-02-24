package com.jusoft.controller.slot;

import com.jusoft.component.slot.Slot;

import java.util.List;

import static com.jusoft.util.TimeUtil.getTimeFrom;
import static java.util.stream.Collectors.toList;

public class SlotResourceFactory {

    public SlotResource createFrom(Slot slot) {
        return new SlotResource(slot.getSlotId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
    }

    public SlotResources createFrom(List<Slot> slots) {
        return new SlotResources(slots.stream().map(this::createFrom).collect(toList()));
    }

}
