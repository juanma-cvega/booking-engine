package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.timer.OpenTime;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.fixture.RoomFixtures.MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO;
import static com.jusoft.bookingengine.fixture.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.SLOT_DURATION_IN_MINUTES;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoomTest {

  private static final Clock CLOCK = Clock.systemUTC();
  private static final long NOT_MULTIPLE_SLOT_DURATION = SLOT_DURATION_IN_MINUTES - 1;
  private static final LocalTime NOW = LocalTime.of(5, 0);
  private static final List<OpenTime> OPEN_TIMES_INVALID = singletonList(OpenTime.of(NOW, NOW.plusMinutes(NOT_MULTIPLE_SLOT_DURATION), CLOCK.getZone(), CLOCK));
  private static final OpenTime FIRST_OPEN = OpenTime.of(NOW.minusMinutes(SLOT_DURATION_IN_MINUTES), NOW, CLOCK.getZone(), CLOCK);
  private static final OpenTime SECOND_OPEN_TIME = OpenTime.of(NOW, NOW.plusMinutes(SLOT_DURATION_IN_MINUTES), CLOCK.getZone(), CLOCK);
  private static final OpenTime THIRD_OPEN_TIME = OpenTime.of(NOW.plusMinutes(SLOT_DURATION_IN_MINUTES), NOW.plusMinutes(SLOT_DURATION_IN_MINUTES * 2), CLOCK.getZone(), CLOCK);
  private static final List<OpenTime> OPEN_TIMES_UNORDERED = Arrays.asList(
    SECOND_OPEN_TIME,
    THIRD_OPEN_TIME,
    FIRST_OPEN);

  @Test
  public void room_with_empty_open_times_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, new ArrayList<>(), AVAILABLE_DAYS))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void room_with_null_open_times_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, null, AVAILABLE_DAYS))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void room_with_empty_available_days_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, new ArrayList<>()))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void room_with_null_available_days_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void room_with_open_times_not_multiple_of_slot_duration_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, OPEN_TIMES_INVALID, AVAILABLE_DAYS))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void room_with_null_slot_creation_strategy_config_info_should_fail_creation() {
    assertThatThrownBy(() -> new Room(ROOM_ID, CLUB_ID, BUILDING_ID, null, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void room_open_times_are_sorted() {
    Room room = new Room(ROOM_ID, CLUB_ID, BUILDING_ID, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO, SLOT_DURATION_IN_MINUTES, OPEN_TIMES_UNORDERED, AVAILABLE_DAYS);
    assertThat(room.getOpenTimesPerDay()).hasSize(3);
    assertThat(room.getOpenTimesPerDay()).containsExactly(FIRST_OPEN, SECOND_OPEN_TIME, THIRD_OPEN_TIME);
  }
}
