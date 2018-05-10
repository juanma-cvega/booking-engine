package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyPreReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotNotAvailableException;
import org.junit.Test;

import java.time.Clock;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotFixtures.ANOTHER_SLOT_USER;
import static com.jusoft.bookingengine.fixture.SlotFixtures.END_TIME;
import static com.jusoft.bookingengine.fixture.SlotFixtures.OPEN_DATE;
import static com.jusoft.bookingengine.fixture.SlotFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotFixtures.SLOT_USER;
import static com.jusoft.bookingengine.fixture.SlotFixtures.START_TIME;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SlotTest {

  @Test
  public void slot_without_creation_time_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, null, OPEN_DATE, AvailableSlotState.getInstance()))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_open_date_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, null, AvailableSlotState.getInstance()))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_state_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void given_slot_current_time_is_within_start_and_end_time_is_open_should_return_true() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isOpen(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()))).isTrue();
  }

  @Test
  public void given_slot_current_time_is_before_start_time_is_open_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isOpen(Clock.fixed(START_TIME.toInstant().plus(1, HOURS), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_current_time_is_equal_to_end_time_is_open_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isOpen(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_when_reserving_the_slot_it_should_change_state_to_reserved() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    Slot slotModified = slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER);
    assertThat(slotModified.getState()).isEqualTo(ReservedState.of(SLOT_USER));
  }

  @Test
  public void given_slot_state_is_available_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_pre_reserved_when_reserving_the_slot_it_should_throw_an_already_reserved_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, PreReservedState.of(SLOT_USER));
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER))
      .isInstanceOf(SlotAlreadyPreReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1)
      .hasFieldOrPropertyWithValue("previousUser", SLOT_USER)
      .hasFieldOrPropertyWithValue("newUser", SLOT_USER);
  }

  @Test
  public void given_slot_state_is_pre_reserved_when_pre_reserving_the_slot_by_the_same_user_it_should_return_itself() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, PreReservedState.of(SLOT_USER));
    Slot newSlot = slot.preReserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER);
    assertThat(slot).isEqualTo(newSlot);
  }

  @Test
  public void given_slot_state_is_pre_reserved_when_pre_reserving_the_slot_by_another_user_it_should_return_itself() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, PreReservedState.of(SLOT_USER));
    assertThatThrownBy(() -> slot.preReserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), ANOTHER_SLOT_USER))
      .isInstanceOf(SlotAlreadyPreReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1)
      .hasFieldOrPropertyWithValue("previousUser", SLOT_USER)
      .hasFieldOrPropertyWithValue("newUser", ANOTHER_SLOT_USER);
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_the_slot_by_the_same_user_it_should_return_itself() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.of(SLOT_USER));
    Slot newSlot = slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER);
    assertThat(slot).isEqualTo(newSlot);
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_the_slot_by_another_user_it_should_return_itself() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.of(SLOT_USER));
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), ANOTHER_SLOT_USER))
      .isInstanceOf(SlotAlreadyReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1)
      .hasFieldOrPropertyWithValue("previousUser", SLOT_USER)
      .hasFieldOrPropertyWithValue("newUser", ANOTHER_SLOT_USER);
  }

  @Test
  public void given_slot_state_is_reserved_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.of(SLOT_USER));
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_reserved_when_pre_reserving_it_should_throw_a_slot_not_in_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.of(SLOT_USER));
    assertThatThrownBy(() -> slot.preReserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER))
      .isInstanceOf(SlotAlreadyReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1)
      .hasFieldOrPropertyWithValue("previousUser", SLOT_USER)
      .hasFieldOrPropertyWithValue("newUser", SLOT_USER);
  }

  @Test
  public void given_slot_state_is_pre_reserved_when_reserving_the_slot_it_should_throw_a_slot_already_reserved_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, PreReservedState.of(SLOT_USER));
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER))
      .isInstanceOf(SlotAlreadyPreReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1)
      .hasFieldOrPropertyWithValue("previousUser", SLOT_USER)
      .hasFieldOrPropertyWithValue("newUser", SLOT_USER);
  }

  @Test
  public void given_slot_state_is_pre_reserved_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, PreReservedState.of(SLOT_USER));
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_created_when_reserving_the_slot_it_should_throw_a_slot_not_available_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER))
      .isInstanceOf(SlotNotAvailableException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_created_when_pre_reserving_the_slot_it_should_change_state_to_pre_reserved() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    Slot slotModified = slot.preReserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()), SLOT_USER);
    assertThat(slotModified.getState()).isEqualTo(PreReservedState.of(SLOT_USER));
  }

  @Test
  public void given_slot_state_is_created_when_making_it_available_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }
}
