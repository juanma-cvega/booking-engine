package com.jusoft.component.slot;

import com.jusoft.util.TimeUtil;

import java.util.function.Supplier;

class SlotFactory {

    private final Supplier<Long> idGenerator;

    SlotFactory(Supplier<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    Slot createFrom(CreateSlotRequest request) {
        return new Slot(idGenerator.get(), request.getRoomId(), TimeUtil.getLocalDateTimeFrom(request.getStartTime()), TimeUtil.getLocalDateTimeFrom(request.getEndTime()));
    }
}
