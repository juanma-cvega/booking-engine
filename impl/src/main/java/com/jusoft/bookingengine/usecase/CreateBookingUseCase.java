package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.member.api.MemberComponent;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBookingUseCase {

  private final BookingComponent bookingComponent;
  private final SlotComponent slotComponent;
  private final AuctionComponent auctionComponent;
  private final MemberComponent memberComponent;

  public BookingView book(CreateBookingCommand createBookingCommand) {
    SlotView slot = slotComponent.find(createBookingCommand.getSlotId());
    if (!memberComponent.isMemberOf(slot.getClubId(), createBookingCommand.getUserId())) {
      throw new UserNotMemberException(createBookingCommand.getUserId(), slot.getClubId());
    } else if (!slotComponent.isSlotOpen(createBookingCommand.getSlotId())) {
      throw new SlotAlreadyStartedException(createBookingCommand.getSlotId());
    } else if (auctionComponent.isAuctionOpenForSlot(createBookingCommand.getSlotId())) {
      throw new SlotPendingAuctionException(createBookingCommand.getSlotId());
    } else {
      return bookingComponent.book(createBookingCommand);
    }
  }
}
