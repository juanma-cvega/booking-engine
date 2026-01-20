package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import java.time.ZonedDateTime;

public interface SlotLifeCycleManagerComponent {

    SlotLifeCycleManagerView createFrom(long roomId, SlotsTimetable slotsTimetable);

    SlotLifeCycleManagerView find(long roomId);

    void replaceSlotsTimetableWith(long roomId, SlotsTimetable slotsTimetable);

    void modifyAuctionConfigFor(long roomId, AuctionConfigInfo auctionConfigInfo);

    void addClassTimetableTo(long roomId, ClassTimetable classTimetable);

    void removeClassTimetableFrom(long roomId, long classId);

    void addPreReservation(long roomId, PreReservation preReservation);

    void removePreReservation(long roomId, ZonedDateTime slotStartTime);

    void findNextSlotStateFor(long slotId, long roomId, ZonedDateTime slotStartTime);

    void notifyOfSlotReservation(long slotId, SlotUser slotUser);
}
