package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.publisher.message.AuctionWinnerFoundMessage;
import com.jusoft.bookingengine.usecase.CreateBookingUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Slf4j
class AuctionWinnerFoundEventListener implements MessageListener {

  private final CreateBookingUseCase createBookingUseCase;

  @EventListener(AuctionWinnerFoundMessage.class)
  public void bookSlotForAuctionWinner(AuctionWinnerFoundMessage event) {
    log.info("AuctionWinnerFoundEvent consumed: auctionId={}, userId={}, slotId={}, roomId={}",
      event.getAuctionId(), event.getAuctionWinnerId(), event.getSlotId(), event.getRoomId());
    //FIXME possible race condition when someone books exactly at the same time the auction ends???
    BookingView bookingCreated = createBookingUseCase.book(new CreateBookingCommand(event.getAuctionWinnerId(), event.getRoomId(), event.getSlotId()));
    log.info("AuctionWinnerFoundEvent processed: auctionId={}, slotId={}, roomId={}, bookingId={}, userId={}",
      event.getAuctionId(), event.getSlotId(), event.getRoomId(), bookingCreated.getId(), bookingCreated.getUserId());
  }
}
