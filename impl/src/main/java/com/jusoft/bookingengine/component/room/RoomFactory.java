package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomFactory {

    private final Supplier<Long> idGenerator;

    Room createFrom(CreateRoomCommand createRoomCommand, long clubId) {
        return new Room(
                idGenerator.get(),
                clubId,
                createRoomCommand.buildingId(),
                createRoomCommand.slotCreationConfigInfo(),
                createRoomCommand.slotDurationInMinutes(),
                createRoomCommand.openTimePerDay(),
                createRoomCommand.availableDays());
    }

    RoomView createFrom(Room room) {
        return new RoomView(
                room.getId(),
                room.getClubId(),
                room.getBuildingId(),
                room.getSlotCreationConfigInfo(),
                room.getSlotDurationInMinutes(),
                room.getOpenTimesPerDay(),
                room.getAvailableDays());
    }
}
