package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;

public record AuctionWinnerFoundEvent(long auctionId, long auctionWinnerId, long slotId)
        implements Event {}
