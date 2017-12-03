package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.usecase.AuctionUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class AuctionFinishedEventListener implements MessageListener<AuctionFinishedEvent> {

  private final AuctionUseCase auctionUseCase;

  @EventListener(AuctionFinishedEvent.class)
  @Override
  public void consume(AuctionFinishedEvent event) {
    log.info("AuctionFinishedEvent consumed: auctionId={}", event.getAuctionId());

    auctionUseCase.finishAuction(event.getAuctionId(), event.getRoomId());
  }
}
