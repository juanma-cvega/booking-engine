package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;

public record AuctionFinishedEvent(long auctionId) implements Event {}
