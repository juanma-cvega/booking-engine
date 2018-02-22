package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingCanceledEvent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.BookingView;
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
class BookingManagerComponentImpl implements BookingManagerComponent {

  private final BookingRepository bookingRepository;
  private final BookingFactory bookingFactory;
  private final MessagePublisher messagePublisher;

  @Override
  public BookingView book(CreateBookingCommand command) {
    Booking newBooking = bookingFactory.create(command);
    bookingRepository.save(newBooking);
    messagePublisher.publish(BookingCreatedEvent.of(newBooking.getId(), newBooking.getUserId(), newBooking.getSlotId()));
    return bookingFactory.create(newBooking);
  }

  @Override
  public void cancel(long userId, long bookingId) {
    bookingRepository.delete(bookingId, deleteBookingIfOwner(userId), () -> new BookingNotFoundException(bookingId));
    messagePublisher.publish(BookingCanceledEvent.of(bookingId));
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
  public BookingView find(long bookingId) {
    return bookingRepository.find(bookingId).map(bookingFactory::create)
      .orElseThrow(() -> new BookingNotFoundException(bookingId));
  }

  @Override
  public List<BookingView> findAllBy(long userId) {
    return bookingFactory.create(bookingRepository.getByUser(userId));
  }

  @Override
  public List<BookingView> findUsersBookingsUntilFor(ZonedDateTime endTime, Set<Long> users) {
    return bookingFactory.create(bookingRepository.findBookingsUntilFor(endTime, users));
  }
}
