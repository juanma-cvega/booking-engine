package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBookingUseCase {

  private final BookingComponent bookingComponent;
  private final SlotComponent slotComponent;
  private final AuctionComponent auctionComponent;

  public BookingView book(CreateBookingCommand createBookingCommand) {
    if (!slotComponent.isSlotOpen(createBookingCommand.getSlotId(), createBookingCommand.getRoomId())) {
      throw new SlotAlreadyStartedException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else if (auctionComponent.isAuctionOpenForSlot(createBookingCommand.getSlotId())) {
      throw new SlotPendingAuctionException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else {
      return bookingComponent.book(createBookingCommand);
    }
  }
}
