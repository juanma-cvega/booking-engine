package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyBookedException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class BookingRepositoryInMemory implements BookingRepository {

  private final Map<Long, Booking> store;

  BookingRepositoryInMemory(Map<Long, Booking> store) {
    this.store = store;
  }

  /**
   * Naive implementation of the database. Locks the whole operation to ensure the constraint that there cannot be
   * two entries with the same slotId
   *
   * @param newBooking booking to save
   * @throws SlotAlreadyBookedException in case the slot is already booked
   */
  //FIXME is repository the one who has to throw a business exception
  @Override
  public synchronized void save(Booking newBooking) throws SlotAlreadyBookedException {
    store.values().stream().filter(booking -> Long.compare(booking.getId(), newBooking.getId()) == 0).findFirst()
      .ifPresent(booking -> {
        throw new SlotAlreadyBookedException(newBooking.getRoomId(), newBooking.getId());
      });
    store.put(newBooking.getId(), newBooking);
  }

  @Override
  public void delete(long bookingId) {
    store.remove(bookingId);
  }

  @Override
  public Optional<Booking> find(long bookingId) {
    return Optional.ofNullable(store.get(bookingId));
  }

  @Override
  public List<Booking> getByUser(long userId) {
    return store.values().stream().filter(booking -> Long.compare(userId, booking.getUserId()) == 0).collect(toList());
  }

  @Override
  public List<Long> findUserWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users) {
    Map<Long, Long> mapUserToNumberOfBookings = store.values().stream()
      .filter(booking -> users.contains(booking.getUserId()))
      .map(Booking::getUserId)
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return mapUserToNumberOfBookings.values().stream().min(Comparator.naturalOrder())
      .map(maxBookings -> mapToUsersWithMinimumBookings(mapUserToNumberOfBookings, maxBookings))
      .orElse(new ArrayList<>());
  }

  private List<Long> mapToUsersWithMinimumBookings(Map<Long, Long> mapUserToNumberOfBookings, Long minBookings) {
    return mapUserToNumberOfBookings.entrySet().stream()
      .filter(entry -> entry.getValue().equals(minBookings))
      .map(Map.Entry::getKey)
      .collect(toList());
  }
}
