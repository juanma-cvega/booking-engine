package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.publisher.message.AuctionWinnerFoundMessage;
import com.jusoft.bookingengine.usecase.slot.ReserveSlotForAuctionWinnerUseCase;
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
    reserveSlotForAuctionWinnerUseCase.reserveSlotForAuctionWinner(event.getSlotId(), event.getAuctionWinnerId());
  }
}
