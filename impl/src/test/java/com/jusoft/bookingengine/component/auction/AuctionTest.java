package com.jusoft.bookingengine.component.auction;

import com.google.common.collect.Sets;
import com.jusoft.bookingengine.component.auction.api.Bid;
import org.junit.Test;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.jusoft.bookingengine.fixture.AuctionFixtures.AUCTION_DURATION_IN_MINUTES;
import static com.jusoft.bookingengine.fixture.AuctionFixtures.AUCTION_ID;
import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.SLOT_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AuctionTest {

  private final Clock clock = Clock.fixed(ZonedDateTime.now().toInstant(), ZoneId.systemDefault());

  @Test
  public void create_auction_fails_with_null_clock() {
    assertThatThrownBy(() -> new Auction(AUCTION_ID, SLOT_ID_1, ROOM_ID, AUCTION_DURATION_IN_MINUTES, null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void create_auction_should_create_start_and_end_time_based_on_clock_and_duration() {
    Auction auction = new Auction(AUCTION_ID, SLOT_ID_1, ROOM_ID, AUCTION_DURATION_IN_MINUTES, clock);
    assertThat(auction.getStartTime()).isEqualTo(ZonedDateTime.now(clock));
    assertThat(auction.getEndTime()).isEqualTo(auction.getStartTime().plusMinutes(AUCTION_DURATION_IN_MINUTES));
  }

  @Test
  public void create_auction_with_bidders_should_contain_the_bidders_passed() {
    Bid bid = new Bid(USER_ID_1, ZonedDateTime.now(clock));
    Set<Bid> bidders = Sets.newHashSet(bid);
    Auction auction = new Auction(AUCTION_ID, SLOT_ID_1, ROOM_ID, AUCTION_DURATION_IN_MINUTES, bidders, clock);
    assertThat(auction.getBidders()).containsExactly(bid);
  }
}
