package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import lombok.experimental.UtilityClass;

import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;

@UtilityClass
public class SlotLifeCycleFixtures {

  public static SlotValidationInfo SLOT_VALIDATION_INFO = SlotValidationInfo.of(SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS);
}
