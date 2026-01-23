package com.jusoft.bookingengine.component.auction;

import static com.jusoft.bookingengine.fixture.AuctionFixtures.AUCTION_ID;
import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.fixture.SlotFixtures.NO_AUCTION_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.SlotFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Sets;
import com.jusoft.bookingengine.component.auction.api.Bid;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AuctionTest {

    private final Clock clock =
            Clock.fixed(ZonedDateTime.now().toInstant(), ZoneId.systemDefault());

    @Test
    void create_auction_with_bidders_should_contain_the_bidders_passed() {
        Bid bid = new Bid(USER_ID_1, ZonedDateTime.now(clock));
        Set<Bid> bidders = Sets.newHashSet(bid);
        Auction auction =
                new Auction(AUCTION_ID, SLOT_ID_1, START_TIME, NO_AUCTION_CONFIG_INFO, bidders);
        assertThat(auction.getBidders()).containsExactly(bid);
    }
}
