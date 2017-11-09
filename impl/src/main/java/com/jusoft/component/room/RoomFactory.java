package com.jusoft.component.room;

import com.jusoft.component.room.api.CreateRoomCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomFactory {

    private final Supplier<Long> idGenerator;

    public Room createFrom(CreateRoomCommand createRoomCommand) {
        return new Room(idGenerator.get(),
                createRoomCommand.getMaxSlots(),
                createRoomCommand.getSlotDurationInMinutes(),
                createRoomCommand.getOpenTimePerDay(),
                createRoomCommand.getAvailableDays(),
                createRoomCommand.isActive());
    }
}
