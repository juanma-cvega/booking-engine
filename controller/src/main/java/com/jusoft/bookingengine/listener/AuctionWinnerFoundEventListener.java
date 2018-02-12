package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.publisher.message.AuctionWinnerFoundMessage;
import com.jusoft.bookingengine.usecase.ReserveSlotForAuctionWinnerUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Slf4j
class AuctionWinnerFoundEventListener implements MessageListener {

  private final ReserveSlotForAuctionWinnerUseCase reserveSlotForAuctionWinnerUseCase;

  @EventListener(AuctionWinnerFoundMessage.class)
  public void bookSlotForAuctionWinner(AuctionWinnerFoundMessage event) {
    log.info("AuctionWinnerFoundEvent consumed: auctionId={}, userId={}, slotId={}",
      event.getAuctionId(), event.getAuctionWinnerId(), event.getSlotId());
    //FIXME possible race condition when someone books exactly at the same time the auction ends???
    reserveSlotForAuctionWinnerUseCase.reserveSlotForAuctionWinner(ReserveSlotCommand.of(event.getSlotId(), event.getAuctionWinnerId()));
//    log.info("AuctionWinnerFoundEvent processed: auctionId={}, slotId={}, roomId={}, bookingId={}, userId={}",
//      event.getAuctionId(), event.getSlotId(), event.getRoomId(), bookingCreated.getId(), bookingCreated.getUserId());
  }
}
