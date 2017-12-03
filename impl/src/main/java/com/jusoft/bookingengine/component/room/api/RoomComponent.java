package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.room.Room;
import com.jusoft.bookingengine.component.timer.OpenDate;

import java.time.ZonedDateTime;

public interface RoomComponent {

  Room create(CreateRoomCommand createRoomCommand);

  Room find(long roomId);

  OpenDate findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId);

  OpenDate findFirstSlotOpenDate(long roomId);

  ZonedDateTime findNextSlotOpeningTime(long roomId, int currentNumberOfSlotsOpen, ZonedDateTime nextSlotToFinishEndDate);

  int getAuctionDurationFor(long roomId);
}
