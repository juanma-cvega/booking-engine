package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;

public record SlotRequiresAuctionEvent(long slotId, AuctionConfigInfo auctionConfigInfo)
        implements Event {}
