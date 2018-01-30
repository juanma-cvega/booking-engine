package com.jusoft.bookingengine.component.auction;

import com.google.common.collect.Sets;
import com.jusoft.bookingengine.component.auction.api.Bid;
import org.junit.Test;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.jusoft.bookingengine.fixture.AuctionFixtures.AUCTION_ID;
import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.END_TIME;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThat;

public class AuctionTest {

  private final Clock clock = Clock.fixed(ZonedDateTime.now().toInstant(), ZoneId.systemDefault());
  
  @Test
  public void create_auction_with_bidders_should_contain_the_bidders_passed() {
    Bid bid = new Bid(USER_ID_1, ZonedDateTime.now(clock));
    Set<Bid> bidders = Sets.newHashSet(bid);
    Auction auction = new Auction(AUCTION_ID, SLOT_ID_1, ROOM_ID, START_TIME, END_TIME, bidders);
    assertThat(auction.getBidders()).containsExactly(bid);
  }
}
