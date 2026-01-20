package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
class InAuctionState implements NextSlotState {

    private final long slotId;
    private final AuctionConfigInfo auctionConfigInfo;
}
