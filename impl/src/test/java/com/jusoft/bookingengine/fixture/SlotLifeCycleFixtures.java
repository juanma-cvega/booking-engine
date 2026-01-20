package com.jusoft.bookingengine.fixture;

import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlotLifeCycleFixtures {

    public static SlotsTimetable SLOT_VALIDATION_INFO =
            SlotsTimetable.of(SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS);
}
