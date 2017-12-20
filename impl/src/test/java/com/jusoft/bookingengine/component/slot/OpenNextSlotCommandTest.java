package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.fixture.CommonFixtures;
import org.junit.Test;

import static com.jusoft.bookingengine.fixture.SlotsFixtures.END_TIME;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OpenNextSlotCommandTest {

  @Test
  public void nullStartTimeFailsConstructor() {
    assertThatThrownBy(() -> new CreateSlotCommand(CommonFixtures.ROOM_ID, null, END_TIME)).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void nullEndTimeFailsConstructor() {
    assertThatThrownBy(() -> new CreateSlotCommand(CommonFixtures.ROOM_ID, START_TIME, null)).isInstanceOf(NullPointerException.class);
  }
}
