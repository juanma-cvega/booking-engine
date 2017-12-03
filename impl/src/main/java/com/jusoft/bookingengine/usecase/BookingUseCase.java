package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.Booking;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.CancelBookingCommand;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookingUseCase {

  private final BookingComponent bookingComponent;
  private final SlotComponent slotComponent;
  private final AuctionComponent auctionComponent;

  public Booking book(CreateBookingCommand createBookingCommand) {
    if (!slotComponent.isSlotOpen(createBookingCommand.getSlotId(), createBookingCommand.getRoomId())) {
      throw new SlotAlreadyStartedException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else if (auctionComponent.isAuctionOpenForSlot(createBookingCommand.getSlotId())) {
      throw new SlotPendingAuctionException(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());
    } else {
      return bookingComponent.book(createBookingCommand);
    }
  }

  //FIXME it makes two calls to database to find the same booking
  public void cancel(CancelBookingCommand cancelBookingCommand) {
    Booking booking = bookingComponent.find(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId());
    if (!slotComponent.isSlotOpen(booking.getSlotId(), booking.getRoomId())) {
      throw new SlotAlreadyStartedException(booking.getSlotId(), booking.getRoomId());
    }
    bookingComponent.cancel(cancelBookingCommand);
  }
} 
