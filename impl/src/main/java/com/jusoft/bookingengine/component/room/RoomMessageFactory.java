package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomMessageFactory {

    RoomCreatedEvent roomCreatedEvent(Room room) {
        return new RoomCreatedEvent(
                room.getId(),
                room.getSlotDurationInMinutes(),
                room.getOpenTimesPerDay(),
                room.getAvailableDays());
    }
}
