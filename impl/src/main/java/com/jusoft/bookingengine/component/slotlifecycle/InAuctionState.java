package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;

record InAuctionState(long slotId, AuctionConfigInfo auctionConfigInfo) implements NextSlotState {}
