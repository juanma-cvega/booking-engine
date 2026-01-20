package com.jusoft.bookingengine.strategy.auctionwinner;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionWinnerStrategyFactory;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import java.time.Clock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LessBookingsWithinPeriodStrategyFactory
        implements AuctionWinnerStrategyFactory<LessBookingsWithinPeriodConfigInfo> {

    private final BookingManagerComponent bookingManagerComponent;
    private final Clock clock;

    @Override
    public LessBookingsWithinPeriodStrategy createInstance(
            LessBookingsWithinPeriodConfigInfo config) {
        return new LessBookingsWithinPeriodStrategy(bookingManagerComponent, clock, config);
    }
}
