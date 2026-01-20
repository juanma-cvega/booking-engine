package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.publisher.Event;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;

public record AuctionRequiredEvent(long slotId, AuctionConfigInfo auctionConfigInfo)
        implements Event {}
