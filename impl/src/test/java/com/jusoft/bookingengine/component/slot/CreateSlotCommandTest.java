package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import org.junit.Test;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateSlotCommandTest {

  @Test
  public void null_open_date_fails_constructor() {
    assertThatThrownBy(() -> CreateSlotCommand.of(ROOM_ID, BUILDING_ID, CLUB_ID, null)).isInstanceOf(NullPointerException.class);
  }

}
