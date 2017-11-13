package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.slot.Slot;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingComponentImpl implements BookingComponent {

  private final SlotComponent slotComponent;
  private final BookingRepository bookingRepository;
  private final BookingFactory bookingFactory;
  private final Clock clock;

  /* The code explicitly doesn't check whether the slot is booked already. That constraint is set at the database level.
  * Validating it here would cause a performance penalty as it would require a call to the database and it won't guarantee
  * consistency in a multithreaded setup*/
  @Override
  public Booking book(CreateBookingCommand createBookingCommand) throws SlotAlreadyStartedException {
    Slot slot = slotComponent.find(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());

    if (slot.isOpen(ZonedDateTime.now(clock))) {
      Booking newBooking = bookingFactory.create(createBookingCommand, slot);
      bookingRepository.save(newBooking);
      return newBooking;
    } else {
      throw new SlotAlreadyStartedException(slot.getId(), slot.getRoomId(), slot.getStartDate());
    }
  }

  //TODO provide a better way of handling validations
  @Override
  public void cancel(CancelBookingCommand cancelBookingCommand) throws BookingNotFoundException {
    Booking booking = bookingRepository.find(cancelBookingCommand.getBookingId())
      .orElseThrow(() -> new BookingNotFoundException(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId()));

    if (booking.canClose(ZonedDateTime.now(clock), cancelBookingCommand.getUserId())) {
      bookingRepository.delete(booking.getId());
    }
  }

  //FIXME normalize validation as it's done in two places
  @Override
  public Booking find(long userId, long bookingId) throws BookingNotFoundException {
    return bookingRepository.find(bookingId)
      .orElseThrow(() -> new BookingNotFoundException(userId, bookingId));
  }

  @Override
  public List<Booking> getFor(long userId) {
    return bookingRepository.getByUser(userId);
  }

}
