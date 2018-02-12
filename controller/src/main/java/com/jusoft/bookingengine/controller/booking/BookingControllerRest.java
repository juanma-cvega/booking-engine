package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingNotFoundException;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(value = "/bookings")
class BookingControllerRest {

  private final BookingComponent bookingComponent;
  private final BookingCommandFactory bookingCommandFactory;
  private final BookingResourceFactory bookingResourceFactory;

  BookingControllerRest(BookingComponent bookingComponent, BookingCommandFactory bookingCommandFactory, BookingResourceFactory bookingResourceFactory) {
    this.bookingComponent = bookingComponent;
    this.bookingCommandFactory = bookingCommandFactory;
    this.bookingResourceFactory = bookingResourceFactory;
  }

  @PostMapping(value = "/room/{roomId}/slot/{slotId}/booking", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public BookingResource book(@PathVariable long roomId, @PathVariable long slotId, @Valid @RequestBody CreateBookingRequest command) {
    log.info("Create booking request received: roomId={}, slotId={}, userId={}", roomId, slotId, command.getUserId());
    BookingView booking = bookingComponent.book(bookingCommandFactory.createFrom(roomId, slotId, command.getUserId()));
    BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
    log.info("Create booking request finished: booking={}", bookingResource);
    return bookingResource;
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}/booking/{bookingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@PathVariable long userId, @PathVariable long bookingId) {
    log.info("Cancel booking request received: userId={}, bookingId={}", userId, bookingId);
    bookingComponent.cancel(bookingCommandFactory.createFrom(userId, bookingId));
    log.info("Cancel booking request finished");
  }

  @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/booking/{bookingId}", produces = "application/json")
  @ResponseBody
  public BookingResource find(@PathVariable long userId, @PathVariable long bookingId) {
    log.info("Find booking request received: userId={}, bookingId={}", userId, bookingId);
    BookingView booking = bookingComponent.find(userId, bookingId);
    BookingResource bookingResource = bookingResourceFactory.createFrom(booking);
    log.info("Find booking request finished: booking={}", bookingResource);
    return bookingResource;
  }

  @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}", produces = "application/json")
  @ResponseBody
  public BookingResources getFor(@PathVariable long userId) {
    log.info("Create booking request received: userId={}", userId);
    List<BookingView> bookings = bookingComponent.getFor(userId);
    BookingResources bookingResources = bookingResourceFactory.createFrom(bookings);
    log.info("Create booking request finished: userId={}, bookings={}", userId, bookingResources.getBookings().size());
    return bookingResources;
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Booking not found")
  @ExceptionHandler(BookingNotFoundException.class)
  public void bookingNotFoundException() {
    //Do nothing
  }

  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Slot already booked")
  @ExceptionHandler(SlotAlreadyReservedException.class)
  public void slotAlreadyBookedException() {
    //Do nothing
  }

  @ResponseStatus(value = HttpStatus.PRECONDITION_REQUIRED, reason = "Slot already started")
  @ExceptionHandler(SlotNotAvailableException.class)
  public void slotAlreadyStartedException() {
    //Do nothing
  }

  @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Booking does not belong to user")
  @ExceptionHandler(WrongBookingUserException.class)
  public void wrongBookingUserException() {
    //Do nothing
  }
}
