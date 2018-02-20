package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelBookingUseCase {

  private final BookingManagerComponent bookingManagerComponent;
  private final SlotManagerComponent slotManagerComponent;

  public void cancel(CancelBookingCommand cancelBookingCommand) {
    BookingView booking = bookingManagerComponent.find(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId());
    if (!slotManagerComponent.isSlotOpen(booking.getSlotId())) {
      throw new SlotNotAvailableException(booking.getSlotId());
    }
    bookingManagerComponent.cancel(cancelBookingCommand);
  }
}
