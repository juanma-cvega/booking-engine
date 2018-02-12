package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelBookingUseCase {

  private final BookingComponent bookingComponent;
  private final SlotComponent slotComponent;

  public void cancel(CancelBookingCommand cancelBookingCommand) {
    BookingView booking = bookingComponent.find(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId());
    if (!slotComponent.isSlotOpen(booking.getSlotId())) {
      throw new SlotNotAvailableException(booking.getSlotId());
    }
    bookingComponent.cancel(cancelBookingCommand);
  }
}
