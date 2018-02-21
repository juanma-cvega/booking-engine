package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetBookingsUseCase {

  private final BookingManagerComponent bookingManagerComponent;

  public List<BookingView> getBookingsFor(long userId) {
    return bookingManagerComponent.findAllBy(userId);
  }
}
