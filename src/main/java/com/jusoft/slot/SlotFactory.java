package com.jusoft.slot;

import java.util.function.Supplier;

import static com.jusoft.util.TimeUtil.getLocalDateTimeFrom;

class SlotFactory {

    private final Supplier<Long> idGenerator;

    public SlotFactory(Supplier<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    Slot createFrom(CreateSlotRequest request) {
        return new Slot(idGenerator.get(), request.getRoomId(), getLocalDateTimeFrom(request.getStartTime()), getLocalDateTimeFrom(request.getEndTime()));
    }
}
