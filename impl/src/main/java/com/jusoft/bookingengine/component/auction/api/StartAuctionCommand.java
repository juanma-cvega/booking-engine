package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;

public record StartAuctionCommand(long referenceId, AuctionConfigInfo auctionConfigInfo)
        implements Command {}
