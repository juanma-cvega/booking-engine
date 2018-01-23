package com.jusoft.bookingengine.component.slot;

import org.junit.Test;

import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.END_TIME;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.START_TIME;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SlotTest {

  @Test
  public void slot_without_creation_time_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, CLUB_ID, null, START_TIME, END_TIME))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_start_time_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, CLUB_ID, START_TIME, null, END_TIME))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_end_time_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, CLUB_ID, START_TIME, START_TIME, null))
      .isInstanceOf(NullPointerException.class);
  }
}
