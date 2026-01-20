package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.publisher.message.AuctionWinnerFoundMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionWinnerFoundMessageFactory
        implements MessageFactory<AuctionWinnerFoundEvent, AuctionWinnerFoundMessage> {

    @Override
    public AuctionWinnerFoundMessage createFrom(AuctionWinnerFoundEvent message) {
        return AuctionWinnerFoundMessage.of(
                message.auctionId(), message.auctionWinnerId(), message.slotId());
    }
}
