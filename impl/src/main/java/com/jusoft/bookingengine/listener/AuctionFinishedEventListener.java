package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.usecase.FinishAuctionUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class AuctionFinishedEventListener implements MessageListener {

  private final FinishAuctionUseCase finishAuctionUseCase;

  @EventListener(AuctionFinishedEvent.class)
  public void finishAuction(AuctionFinishedEvent event) {
    log.info("AuctionFinishedEvent consumed: auctionId={}", event.getAuctionId());

    finishAuctionUseCase.finishAuction(event.getAuctionId(), event.getRoomId());
  }
}
