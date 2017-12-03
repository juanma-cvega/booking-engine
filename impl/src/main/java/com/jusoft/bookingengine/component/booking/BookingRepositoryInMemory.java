package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyBookedException;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
  @Override
  public synchronized void save(Booking newBooking) throws SlotAlreadyBookedException {
    store.values().stream().filter(booking -> Long.compare(booking.getSlotId(), newBooking.getSlotId()) == 0).findFirst()
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
  public Set<Long> findUserWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users) {
    Set<Long> usersWithAtLeastOneBooking = store.values().stream().map(Booking::getUserId).collect(toSet());
    Map<Long, Long> usersWithoutPreviousBookings = users.stream()
      .filter(userId -> !usersWithAtLeastOneBooking.contains(userId))
      .collect(Collectors.toMap(Function.identity(), userId -> 0L));
    Map<Long, Long> mapUserToNumberOfBookings = store.values().stream()
      .filter(booking -> users.contains(booking.getUserId()))
      .map(Booking::getUserId)
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    mapUserToNumberOfBookings.putAll(usersWithoutPreviousBookings);

    Set<Long> usersFound = users;
    if (!mapUserToNumberOfBookings.isEmpty()) {
      usersFound = mapUserToNumberOfBookings.values().stream().min(Comparator.naturalOrder())
        .map(maxBookings -> mapToUsersWithMinimumBookings(mapUserToNumberOfBookings, maxBookings))
        .orElse(new HashSet<>());
    }
    return usersFound;
  }

  private Set<Long> mapToUsersWithMinimumBookings(Map<Long, Long> mapUserToNumberOfBookings, Long minBookings) {
    return mapUserToNumberOfBookings.entrySet().stream()
      .filter(entry -> entry.getValue().equals(minBookings))
      .map(Map.Entry::getKey)
      .collect(toSet());
  }
}
