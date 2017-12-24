package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import lombok.experimental.UtilityClass;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_ID_1;

@UtilityClass
public class AuctionFixtures {

  public static final int AUCTION_ID = 5;

  public static final AuctionFinishedEvent AUCTION_FINISHED_EVENT = new AuctionFinishedEvent(AUCTION_ID, ROOM_ID, SLOT_ID_1);
  public static final AuctionWinnerFoundEvent AUCTION_WINNER_FOUND_EVENT = new AuctionWinnerFoundEvent(AUCTION_ID, USER_ID_1, SLOT_ID_1, ROOM_ID);
}
