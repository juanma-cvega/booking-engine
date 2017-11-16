package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingComponentImpl implements BookingComponent {

  private final SlotComponent slotComponent;
  private final AuctionComponent auctionComponent;
  private final BookingRepository bookingRepository;
  private final BookingFactory bookingFactory;
  private final Clock clock;

  /**
   * Creates a {@link Booking} for the provided slot for the user specified in the request. It can throw an exception in case the
   * slot is already started or if it is running an auction.
   * The code doesn't explicitly check whether the slot is booked already. That constraint is set at the database level.
   * Validating it here would cause a performance penalty as it would require a call to the database and it won't guarantee
   * consistency in a multithreaded setup
   *
   * @param createBookingCommand {@link CreateBookingCommand} containing the slotId, the roomId and the userId.
   * @return the created booking
   * @throws SlotAlreadyStartedException in case the slot is already started.
   * @throws SlotPendingAuctionException in case the slot has an auction pending.
   */
  @Override
  public Booking book(CreateBookingCommand createBookingCommand) {
    if (!slotComponent.isSlotOpen(createBookingCommand.getSlotId(), createBookingCommand.getRoomId())) {
      throw new SlotAlreadyStartedException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else if (!auctionComponent.isAuctionOpenForSlot(createBookingCommand.getSlotId())) {
      throw new SlotPendingAuctionException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else {
      Booking newBooking = bookingFactory.create(createBookingCommand);
      bookingRepository.save(newBooking);
      return newBooking;
    }
  }

  //TODO provide a better way of handling validations
  @Override
  public void cancel(CancelBookingCommand cancelBookingCommand) throws BookingNotFoundException {
    Booking booking = bookingRepository.find(cancelBookingCommand.getBookingId())
      .orElseThrow(() -> new BookingNotFoundException(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId()));

    if (booking.canClose(cancelBookingCommand.getUserId(), slotComponent)) {
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

  /**
   * Find the userId from the list of {@link Booking}s that has booked the less number of times within the period
   * from now to the given end time. In case of a draw, it returns all elements userIds.
   *
   * @param endTime end date for the search.
   * @param users   userIds to use to filter out from the list of {@link Booking}s.
   * @return list of users that matched the criteria
   */
  @Override
  public List<Long> findUsersWithLessBookingsUntil(ZonedDateTime endTime, Set<Long> users) {
    return bookingRepository.findUserWithLessBookingsUntil(endTime, users);
  }

}
