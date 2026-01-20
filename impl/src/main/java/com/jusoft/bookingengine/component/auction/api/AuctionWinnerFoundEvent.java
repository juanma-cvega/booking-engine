package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class AuctionWinnerFoundEvent implements Event {

    private final long auctionId;
    private final long auctionWinnerId;
    private final long slotId;
}
