package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.strategy.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LessBookingsWithinPeriodStrategy implements AuctionWinnerStrategy {

  private final BookingComponent bookingComponent;
  private final Clock clock;
  private final LessBookingsWithinPeriodConfigInfo config;

  @Override
  public Optional<Long> findWinner(Set<Bid> bids) {
    Optional<Long> winner = Optional.empty();
    if (!bids.isEmpty()) {
      ZonedDateTime endPeriod = ZonedDateTime.now(clock).plusDays(config.getEndRangeTimeInDays());
      Set<Long> buyers = bids.stream().map(Bid::getUserId).collect(toSet());
      Set<Long> usersWithLessBookings = bookingComponent.findUsersWithLessBookingsUntil(endPeriod, buyers);
      winner = Optional.of(findFirstCreatedBidFrom(bids, usersWithLessBookings));
    }
    return winner;
  }

  private long findFirstCreatedBidFrom(Set<Bid> bids, Set<Long> usersWithLessBookings) {
    return bids.stream()
      .filter(byUserIn(usersWithLessBookings))
      .min(comparing(Bid::getCreationTime))
      .orElseThrow(() -> new IllegalArgumentException("No bids found")) //Not happening
      .getUserId();
  }

  private Predicate<Bid> byUserIn(Set<Long> usersWithLessBookings) {
    return bid -> usersWithLessBookings.contains(bid.getUserId());
  }
}
