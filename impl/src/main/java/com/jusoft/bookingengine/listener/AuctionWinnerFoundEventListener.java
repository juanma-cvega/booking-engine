package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.usecase.CreateBookingUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Slf4j
public class AuctionWinnerFoundEventListener implements MessageListener {

  private final CreateBookingUseCase createBookingUseCase;

  @EventListener(AuctionWinnerFoundEvent.class)
  public void bookSlotForAuctionWinner(AuctionWinnerFoundEvent event) {
    log.info("AuctionWinnerFoundEvent consumed: auctionId={}, auctionWinner={}, slotId={}, roomId={}",
      event.getAuctionId(), event.getAuctionWinnerId(), event.getSlotId(), event.getRoomId());
    //FIXME possible race condition when someone books exactly at the same time the auction ends???
    createBookingUseCase.book(new CreateBookingCommand(event.getAuctionWinnerId(), event.getRoomId(), event.getSlotId()));
  }
}
