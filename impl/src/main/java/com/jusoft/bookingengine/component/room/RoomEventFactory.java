package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomEventFactory {

  RoomCreatedEvent roomCreatedEvent(Room room) {
    return new RoomCreatedEvent(
      room.getId(),
      room.getMaxSlots(),
      room.getSlotDurationInMinutes(),
      room.getOpenTimesPerDay(),
      room.getAvailableDays(),
      room.isActive());

  }

}
