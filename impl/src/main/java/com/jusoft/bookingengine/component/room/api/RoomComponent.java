package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerStrategyType;
import com.jusoft.bookingengine.component.room.Room;

import java.time.ZonedDateTime;

public interface RoomComponent {

  Room create(CreateRoomCommand createRoomCommand);

  Room find(long roomId);

  void openNextSlotFor(long roomId);

  void scheduleComingSlotFor(long roomId);

  ZonedDateTime findAuctionEndTimeFor(long roomId, long slotId);

  AuctionWinnerStrategyType findAuctionWinnerStrategyTypeFor(long roomId);
}
