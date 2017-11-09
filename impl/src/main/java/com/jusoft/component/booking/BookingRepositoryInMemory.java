package com.jusoft.component.booking;

import com.jusoft.component.booking.api.SlotAlreadyBookedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    store.values().stream().filter(booking -> Long.compare(booking.getSlot().getId(), newBooking.getSlot().getId()) == 0).findFirst()
      .ifPresent(booking -> {
        throw new SlotAlreadyBookedException(newBooking.getSlot().getRoomId(), newBooking.getSlot().getId());
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
}
