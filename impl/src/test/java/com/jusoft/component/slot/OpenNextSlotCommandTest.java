package com.jusoft.component.slot;

import com.jusoft.component.slot.api.CreateSlotCommand;
import org.junit.Test;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.slot.SlotsFixtures.END_TIME;
import static com.jusoft.component.slot.SlotsFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OpenNextSlotCommandTest {

  @Test
  public void nullStartTimeFailsConstructor() {
    assertThatThrownBy(() -> new CreateSlotCommand(ROOM_ID, null, END_TIME)).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void nullEndTimeFailsConstructor() {
    assertThatThrownBy(() -> new CreateSlotCommand(ROOM_ID, START_TIME, null)).isInstanceOf(NullPointerException.class);
  }
}
