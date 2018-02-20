package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import org.junit.Test;

import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.STATE;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.OPEN_DATE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OpenNextSlotCommandTest {

  @Test
  public void null_open_date_fails_constructor() {
    assertThatThrownBy(() -> CreateSlotCommand.of(ROOM_ID, null, STATE)).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void null_state_fails_constructor() {
    assertThatThrownBy(() -> CreateSlotCommand.of(ROOM_ID, OPEN_DATE, null)).isInstanceOf(NullPointerException.class);
  }
}
