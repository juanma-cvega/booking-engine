package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class AuctionFinishedEvent implements Event {

    private final long auctionId;
}
