package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuctionWinnerFoundEventListener implements MessageListener<AuctionWinnerFoundEvent> {

  private final BookingComponent bookingComponent;

  @Override
  public void consume(AuctionWinnerFoundEvent event) {
    log.info("AuctionWinnerFoundEvent consumed: auctionId={}, auctionWinner={}, slotId={}, roomId={}",
      event.getAuctionId(), event.getAuctionWinnerId(), event.getSlotId(), event.getRoomId());
    //FIXME possible race condition when someone books exactly at the same time the auction ends???
    bookingComponent.book(new CreateBookingCommand(event.getAuctionWinnerId(), event.getRoomId(), event.getSlotId()));
  }
}
