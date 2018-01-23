package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenDate;

import java.time.ZonedDateTime;

public interface RoomComponent {

  RoomView create(CreateRoomCommand createRoomCommand, long clubId);

  RoomView find(long roomId);

  OpenDate findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId);

  OpenDate findFirstSlotOpenDate(long roomId);

  int getAuctionDurationFor(long roomId);
}
