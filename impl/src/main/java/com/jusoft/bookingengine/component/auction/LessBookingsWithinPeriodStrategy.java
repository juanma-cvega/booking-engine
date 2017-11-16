package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.LessBookingsWithinPeriodConfig;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class LessBookingsWithinPeriodStrategy implements AuctionWinnerStrategy<LessBookingsWithinPeriodConfig> {

  private final BookingComponent bookingComponent;
  private final Clock clock;

  @Override
  public long findWinner(Set<Bid> bids, LessBookingsWithinPeriodConfig config) {
    long winner = -1;
    if (!bids.isEmpty()) {
      ZonedDateTime endTime = ZonedDateTime.now(clock).plusDays(config.getEndRangeTimeInDays());
      Set<Long> buyers = bids.stream().map(Bid::getUserId).collect(toSet());
      List<Long> usersWithLessBookings = bookingComponent.findUsersWithLessBookingsUntil(endTime, buyers);
      if (usersWithLessBookings.size() == 1) {
        winner = usersWithLessBookings.get(0);
      } else {
        Bid firstBid = bids.stream().min(Comparator.comparing(Bid::getCreationTime)).get();
        winner = firstBid.getUserId();
      }
    }
    return winner;
  }
}
