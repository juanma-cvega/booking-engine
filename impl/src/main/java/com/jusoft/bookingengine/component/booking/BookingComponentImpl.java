package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingCanceledEvent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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
  public BookingView book(CreateBookingCommand createBookingCommand) {
    Booking newBooking = bookingFactory.create(createBookingCommand);
    bookingRepository.save(newBooking);
    messagePublisher.publish(new BookingCreatedEvent(newBooking.getId(), newBooking.getUserId(), newBooking.getSlotId()));
    return bookingFactory.create(newBooking);
  }

  @Override
  public void cancel(CancelBookingCommand cancelBookingCommand) {
    boolean isDeleted = bookingRepository.delete(cancelBookingCommand.getBookingId(), deleteBookingIfOwner(cancelBookingCommand.getUserId()));
    if (isDeleted) {
      messagePublisher.publish(new BookingCanceledEvent(cancelBookingCommand.getBookingId()));
    }
  }

  private Predicate<Booking> deleteBookingIfOwner(long userId) {
    return bookingToDelete -> {
      if (!bookingToDelete.isOwner(userId)) {
        throw new WrongBookingUserException(userId, bookingToDelete.getUserId(), bookingToDelete.getId());
      }
      return true;
    };
  }

  @Override
  public BookingView find(long userId, long bookingId) {
    return bookingRepository.find(bookingId).map(bookingFactory::create)
      .orElseThrow(() -> new BookingNotFoundException(userId, bookingId));
  }

  @Override
  public List<BookingView> findAllBy(long userId) {
    return bookingFactory.create(bookingRepository.getByUser(userId));
  }

  @Override
  public List<BookingView> getFor(long userId) {
    return bookingFactory.create(bookingRepository.getByUser(userId));
  }

  @Override
  public List<BookingView> findUsersBookingsUntilFor(ZonedDateTime endTime, Set<Long> users) {
    return bookingFactory.create(bookingRepository.findBookingsUntilFor(endTime, users));
  }
}
