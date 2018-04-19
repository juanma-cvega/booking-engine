package com.jusoft.bookingengine.component.room.api;

import java.time.ZonedDateTime;

public interface RoomManagerComponent {

  RoomView create(CreateRoomCommand createRoomCommand, long clubId);

  RoomView find(long roomId);

  NextSlotConfig findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId);

  NextSlotConfig findFirstSlotOpenDate(long roomId);
}
