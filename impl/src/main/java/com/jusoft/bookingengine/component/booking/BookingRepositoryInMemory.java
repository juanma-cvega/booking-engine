package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;
import static java.util.stream.Collectors.toList;

class BookingRepositoryInMemory implements BookingRepository {

  private final Map<Long, Booking> store;
  private final Lock lock;

  BookingRepositoryInMemory(Map<Long, Booking> store) {
    this.store = store;
    lock = new ReentrantLock();
  }

  /**
   * Naive implementation of the database. Locks the whole operation to ensure the constraint that there cannot be
   * two entries with the same slotId
   *
   * @param newBooking booking to save
   * @throws SlotAlreadyReservedException in case the slot is already booked
   */
  @Override
  public void save(Booking newBooking) {
    withLock(lock, () -> {
      store.values().stream().filter(booking -> Long.compare(booking.getSlotId(), newBooking.getSlotId()) == 0).findFirst()
        .ifPresent(booking -> {
          throw new SlotAlreadyReservedException(newBooking.getSlotId());
        });
      store.put(newBooking.getId(), newBooking);
    });
  }

  @Override
  public boolean delete(long bookingId, Predicate<Booking> predicate) {
    boolean isRemoved = false;
    Booking booking = find(bookingId).orElseThrow(() -> new BookingNotFoundException(0, bookingId));
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
      return Optional.of(copyBooking(value));
    }
    return Optional.empty();
  }

  private Booking copyBooking(Booking value) {
    return new Booking(value.getId(),
      value.getUserId(),
      value.getBookingTime(),
      value.getSlotId());
  }

  @Override
  public List<Booking> getByUser(long userId) {
    return store.values().stream()
      .filter(booking -> Long.compare(userId, booking.getUserId()) == 0)
      .map(this::copyBooking)
      .collect(toList());
  }

  @Override
  public List<Booking> findBookingsUntilFor(ZonedDateTime endTime, Set<Long> users) {
    return store.values().stream()
      .filter(byBookingBelongsToUserFrom(users))
      .filter(byBookingCreatedBeforeOrAt(endTime))
      .map(this::copyBooking)
      .collect(toList());
  }

  private Predicate<Booking> byBookingBelongsToUserFrom(Set<Long> users) {
    return booking -> users.contains(booking.getUserId());
  }

  private Predicate<Booking> byBookingCreatedBeforeOrAt(ZonedDateTime endTime) {
    return booking -> booking.getBookingTime().isBefore(endTime) || booking.getBookingTime().isEqual(endTime);
  }
}
