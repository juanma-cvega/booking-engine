package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Event;

public record AuctionStartedEvent(long auctionId, long referenceId, OpenDate openDate)
        implements Event {}
