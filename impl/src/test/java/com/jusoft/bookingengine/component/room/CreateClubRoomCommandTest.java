package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import org.junit.Test;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateClubRoomCommandTest {

  @Test
  public void null_slots_creation_strategy_config_info_should_fail_constructor() {
    assertThatThrownBy(() -> CreateRoomCommand.of(BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, null, AVAILABLE_DAYS))
      .isInstanceOf(NullPointerException.class);
  }


  @Test
  public void null_open_times_should_fail_constructor() {
    assertThatThrownBy(() -> CreateRoomCommand.of(BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, null, AVAILABLE_DAYS))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void null_available_days_should_fail_constructor() {
    assertThatThrownBy(() -> CreateRoomCommand.of(BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, null))
      .isInstanceOf(NullPointerException.class);
  }

}
