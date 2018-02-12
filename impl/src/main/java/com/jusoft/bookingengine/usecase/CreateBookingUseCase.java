package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBookingUseCase {

  private final BookingComponent bookingComponent;

  public BookingView book(CreateBookingCommand createBookingCommand) {
    return bookingComponent.book(createBookingCommand);
  }
}
