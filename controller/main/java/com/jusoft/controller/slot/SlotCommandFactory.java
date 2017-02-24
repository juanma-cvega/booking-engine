package com.jusoft.controller.slot;

import com.jusoft.component.slot.CreateSlotCommand;

import static com.jusoft.util.TimeUtil.getLocalDateTimeFrom;

class SlotCommandFactory {
    public CreateSlotCommand createFrom(CreateSlotRequest command) {
        return new CreateSlotCommand(command.getRoomId(), getLocalDateTimeFrom(command.getStartTime()), getLocalDateTimeFrom(command.getEndTime()));
    }
}
