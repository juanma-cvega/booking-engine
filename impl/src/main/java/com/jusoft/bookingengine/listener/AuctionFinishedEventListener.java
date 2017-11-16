package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuctionFinishedEventListener implements MessageListener<AuctionFinishedEvent> {

  private final AuctionComponent auctionComponent;

  @Override
  public void consume(AuctionFinishedEvent event) {
    log.info("AuctionFinishedEvent consumed: auctionId={}", event.getAuctionId());
    auctionComponent.finishAuction(event.getAuctionId());
  }
}
