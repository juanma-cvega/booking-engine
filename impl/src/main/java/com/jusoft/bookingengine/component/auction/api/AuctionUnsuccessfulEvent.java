package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;

public record AuctionUnsuccessfulEvent(long auctionId, long referenceId) implements Event {}
