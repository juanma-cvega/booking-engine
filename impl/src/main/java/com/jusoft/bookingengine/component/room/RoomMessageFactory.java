package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.auction.api.StartAuctionCommand;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomMessageFactory {

  private final Clock clock;

  RoomCreatedEvent roomCreatedEvent(Room room) {
    return new RoomCreatedEvent(
      room.getId(),
      room.getSlotDurationInMinutes(),
      room.getOpenTimesPerDay(),
      room.getAvailableDays(),
      room.isActive(),
      room.getAuctionConfigInfo());
  }

  StartAuctionCommand startAuctionCommand(Room room, long slotId) {
    return new StartAuctionCommand(slotId, room.getAuctionConfigInfo());
  }
}
