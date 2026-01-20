package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.Data;

@Data(staticConstructor = "of")
public class StartAuctionCommand implements Command {

    private final long referenceId;
    private final AuctionConfigInfo auctionConfigInfo;
}
