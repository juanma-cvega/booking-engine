package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.publisher.message.AuctionFinishedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuctionFinishedMessageFactory
        implements MessageFactory<AuctionFinishedEvent, AuctionFinishedMessage> {

    @Override
    public AuctionFinishedMessage createFrom(AuctionFinishedEvent message) {
        return AuctionFinishedMessage.of(message.getAuctionId());
    }
}
