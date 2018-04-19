package com.jusoft.bookingengine.component.booking;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

class BookingRepositoryInMemory implements BookingRepository {

  private final Map<Long, Booking> store;

  BookingRepositoryInMemory(Map<Long, Booking> store) {
    this.store = store;
  }

  @Override
  public void save(Booking newBooking) {
    store.put(newBooking.getId(), newBooking);
  }

  @Override
  public boolean delete(long bookingId, Predicate<Booking> predicate, Supplier<RuntimeException> notEntityFoundException) {
    boolean isRemoved = false;
    Booking booking = find(bookingId).orElseThrow(notEntityFoundException);
    if (predicate.test(booking)) {
      Booking bookingRemoved = store.remove(bookingId);
      isRemoved = bookingRemoved != null;
    }
    return isRemoved;
  }

  @Override
  public Optional<Booking> find(long bookingId) {
    Booking value = store.get(bookingId);
    if (value != null) {
      return Optional.of(value);
    }
    return Optional.empty();
  }

  @Override
  public List<Booking> getByUser(long userId) {
    return store.values().stream()
      .filter(booking -> Long.compare(userId, booking.getUserId()) == 0)
      .collect(toList());
  }

  @Override
  public List<Booking> findBookingsUntilFor(ZonedDateTime endTime, Set<Long> users) {
    return store.values().stream()
      .filter(byBookingBelongsToUserFrom(users))
      .filter(byBookingCreatedBeforeOrAt(endTime))
      .collect(toList());
  }

  private Predicate<Booking> byBookingBelongsToUserFrom(Set<Long> users) {
    return booking -> users.contains(booking.getUserId());
  }

  private Predicate<Booking> byBookingCreatedBeforeOrAt(ZonedDateTime endTime) {
    return booking -> booking.getBookingTime().isBefore(endTime) || booking.getBookingTime().isEqual(endTime);
  }
}
