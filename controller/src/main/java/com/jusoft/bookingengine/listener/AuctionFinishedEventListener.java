package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.publisher.message.AuctionFinishedMessage;
import com.jusoft.bookingengine.usecase.auction.FinishAuctionUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class AuctionFinishedEventListener implements MessageListener {

  private final FinishAuctionUseCase finishAuctionUseCase;

  @EventListener(AuctionFinishedMessage.class)
  public void finishAuction(AuctionFinishedMessage event) {
    log.info("AuctionFinishedEvent consumed: auctionId={}", event.getAuctionId());
    finishAuctionUseCase.finishAuction(event.getAuctionId());
    log.info("AuctionFinishedEvent processed: auctionId={}");
  }
}
