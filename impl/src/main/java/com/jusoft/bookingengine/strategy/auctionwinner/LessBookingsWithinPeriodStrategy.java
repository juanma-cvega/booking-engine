package com.jusoft.bookingengine.strategy.auctionwinner;

import com.jusoft.bookingengine.component.auction.api.Bid;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategy;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LessBookingsWithinPeriodStrategy implements AuctionWinnerStrategy {

  private final BookingManagerComponent bookingManagerComponent;
  private final Clock clock;
  private final LessBookingsWithinPeriodConfigInfo config;

  @Override
  public Optional<Long> findWinner(Set<Bid> bids) {
    Optional<Long> winner = Optional.empty();
    if (!bids.isEmpty()) {
      ZonedDateTime endPeriod = ZonedDateTime.now(clock).plusDays(config.getEndRangeTimeInDays());
      Set<Long> bidders = bids.stream().map(Bid::getUserId).collect(toSet());
      List<BookingView> usersBookingsFound = bookingManagerComponent.findBookingsUntilFor(endPeriod, bidders);
      Set<Long> usersWithLessBookings = bidders.stream()
        .collect(groupingByUserToNumberOfBookings(usersBookingsFound)).entrySet().stream()
        .collect(groupingByNumberOfBookingsToUsers()).entrySet().stream()
        .min(numberOfBookings())
        .map(Map.Entry::getValue)
        .orElse(new HashSet<>());

      winner = findFirstCreatedBidFrom(bids, usersWithLessBookings);
    }
    return winner;
  }

  private long bookingsFor(List<BookingView> bookings, Long user) {
    return bookings.stream()
      .filter(booking -> booking.getUserId() == user)
      .count();
  }

  private Collector<Long, ?, Map<Long, Long>> groupingByUserToNumberOfBookings(List<BookingView> usersBookingsFound) {
    return toMap(user -> user, user -> bookingsFor(usersBookingsFound, user));
  }

  private Collector<Map.Entry<Long, Long>, ?, Map<Long, Set<Long>>> groupingByNumberOfBookingsToUsers() {
    return groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toSet()));
  }

  private Comparator<Map.Entry<Long, Set<Long>>> numberOfBookings() {
    return Map.Entry.comparingByKey();
  }

  private Optional<Long> findFirstCreatedBidFrom(Set<Bid> bids, Set<Long> usersWithLessBookings) {
    return bids.stream()
      .filter(byUserIn(usersWithLessBookings))
      .min(comparing(Bid::getCreationTime))
      .map(Bid::getUserId);
  }

  private Predicate<Bid> byUserIn(Set<Long> usersWithLessBookings) {
    return bid -> usersWithLessBookings.contains(bid.getUserId());
  }
}
