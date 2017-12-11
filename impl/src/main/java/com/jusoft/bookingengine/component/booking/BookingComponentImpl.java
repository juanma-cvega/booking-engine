package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingComponentImpl implements BookingComponent {

  private final BookingRepository bookingRepository;
  private final BookingFactory bookingFactory;
  private final MessagePublisher messagePublisher;

  /**
   * Creates a {@link Booking} for the provided slot for the user specified in the request.
   * The code doesn't explicitly check whether the slot is booked already. That constraint is set at the database level.
   * Validating it here would cause a performance penalty as it would require a call to the database and it won't guarantee
   * consistency in a multithreaded setup
   *
   * @param createBookingCommand {@link CreateBookingCommand} containing the slotId, the roomId and the userId.
   * @return the created booking
   */
  @Override
  public Booking book(CreateBookingCommand createBookingCommand) {
    Booking newBooking = bookingFactory.create(createBookingCommand);
    bookingRepository.save(newBooking);
    messagePublisher.publish(new BookingCreatedEvent(newBooking.getId(), newBooking.getUserId(), newBooking.getSlotId()));
    return newBooking;
  }

  @Override
  public void cancel(CancelBookingCommand cancelBookingCommand) {
    Booking booking = bookingRepository.find(cancelBookingCommand.getBookingId())
      .orElseThrow(() -> new BookingNotFoundException(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId()));
    if (!booking.isOwner(cancelBookingCommand.getUserId())) {
      throw new WrongBookingUserException(cancelBookingCommand.getUserId(), booking.getUserId(), booking.getId());
    }
    bookingRepository.delete(cancelBookingCommand.getBookingId());
  }

  @Override
  public Booking find(long userId, long bookingId) {
    return bookingRepository.find(bookingId)
      .orElseThrow(() -> new BookingNotFoundException(userId, bookingId));
  }

  @Override
  public List<Booking> findAllBy(long userId) {
    return bookingRepository.getByUser(userId);
  }

  @Override
  public List<Booking> getFor(long userId) {
    return bookingRepository.getByUser(userId);
  }

  @Override
  public List<Booking> findUsersBookingsUntilFor(ZonedDateTime endTime, Set<Long> users) {
    return bookingRepository.findBookingsUntilFor(endTime, users);
  }
}
