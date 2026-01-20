package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotLifeCycleManagerView {

    private final long roomId;
    private final SlotsTimetable slotsTimetable;
    private final AuctionConfigInfo auctionConfigInfo;
    private final List<PreReservation> preReservations;
    private final Map<Long, ClassTimetable> classesTimetable;
}
