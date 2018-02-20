package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBookingUseCase {

  private final BookingManagerComponent bookingManagerComponent;

  public BookingView book(CreateBookingCommand createBookingCommand) {
    return bookingManagerComponent.book(createBookingCommand);
  }
}
