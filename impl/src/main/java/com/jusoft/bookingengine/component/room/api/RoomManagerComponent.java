package com.jusoft.bookingengine.component.room.api;

import java.time.ZonedDateTime;

public interface RoomManagerComponent {

  RoomView create(CreateRoomCommand createRoomCommand);

  RoomView find(long roomId);

  NextSlotConfig findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId);

  NextSlotConfig findFirstSlotOpenDate(long roomId);

  void verifyAuctionRequirementFor(long roomId, long slotId);
}
