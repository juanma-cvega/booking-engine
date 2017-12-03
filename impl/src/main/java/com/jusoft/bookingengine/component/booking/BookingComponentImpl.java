package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
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

  //FIXME provide a compute method at the repository level to avoid the need of locking
  @Override
  public void cancel(CancelBookingCommand cancelBookingCommand) throws BookingNotFoundException {
    Booking booking = find(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId());
    if (booking.canClose(cancelBookingCommand.getUserId())) {
      bookingRepository.delete(booking.getId());
    }
  }

  @Override
  public Booking find(long userId, long bookingId) throws BookingNotFoundException {
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

  /**
   * Find the userId from the list of {@link Booking}s that has booked the less number of times within the period
   * from now to the given end time. In case of a draw, it returns all elements userIds.
   *
   * @param endTime end date for the search.
   * @param users   userIds to use to filter out from the list of {@link Booking}s.
   * @return list of users that matched the criteria
   */
  @Override
  public Set<Long> findUsersWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users) {
    return bookingRepository.findUserWithLessBookingsUntil(endTime, users);
  }

}
