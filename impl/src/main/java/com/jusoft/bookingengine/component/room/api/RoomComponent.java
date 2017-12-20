package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenDate;

import java.time.ZonedDateTime;

public interface RoomComponent {

  RoomView create(CreateRoomCommand createRoomCommand);

  RoomView find(long roomId);

  OpenDate findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId);

  OpenDate findFirstSlotOpenDate(long roomId);

  ZonedDateTime findNextSlotCreationTime(long roomId, int currentNumberOfSlotsOpen, ZonedDateTime nextSlotToFinishEndDate);

  int getAuctionDurationFor(long roomId);
}
