package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotPendingAuctionException;
import org.junit.Test;

import java.time.Clock;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_ID;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.END_TIME;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.OPEN_DATE;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.bookingengine.fixture.SlotsFixtures.START_TIME;
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
  public void given_slot_state_is_available_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isAvailable(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_and_current_time_is_before_start_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isAvailable(Clock.fixed(START_TIME.toInstant().plus(1, HOURS), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_and_current_time_is_equal_to_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_reserved_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.getInstance());
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_in_auction_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, InAuctionState.getInstance());
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_when_reserving_the_slot_it_should_change_state_to_reserved() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    Slot slotModified = slot.reserve(Clock.fixed(START_TIME.minusMinutes(10).toInstant(), START_TIME.getZone()));
    assertThat(slotModified.getState()).isEqualTo(ReservedState.getInstance());
  }

  @Test
  public void given_slot_state_is_available_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_available_when_reserving_for_winner_it_should_throw_a_slot_not_in_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, AvailableSlotState.getInstance());
    assertThatThrownBy(slot::reserveForAuctionWinner)
      .isInstanceOf(SlotNotInAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_the_slot_it_should_throw_an_already_reserved_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.getInstance());
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone())))
      .isInstanceOf(SlotAlreadyReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_reserved_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_for_winner_it_should_throw_a_slot_not_in_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, ReservedState.getInstance());
    assertThatThrownBy(slot::reserveForAuctionWinner)
      .isInstanceOf(SlotNotInAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_in_auction_when_reserving_the_slot_it_should_throw_a_slot_pending_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, InAuctionState.getInstance());
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone())))
      .isInstanceOf(SlotPendingAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_in_auction_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, InAuctionState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }

  @Test
  public void given_slot_state_is_created_when_reserving_the_slot_it_should_throw_a_slot_not_available_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone())))
      .isInstanceOf(SlotNotAvailableException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_created_when_reserving_for_winner_the_slot_it_should_throw_a_slot_not_available_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    assertThatThrownBy(slot::reserveForAuctionWinner)
      .isInstanceOf(SlotNotInAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_created_when_making_wait_for_auction_it_should_change_state_to_in_auction() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    Slot slotModified = slot.makeWaitForAuction();
    assertThat(slotModified.getState()).isEqualTo(InAuctionState.getInstance());
  }

  @Test
  public void given_slot_state_is_created_when_making_it_available_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, BUILDING_ID, CLUB_ID, START_TIME, OPEN_DATE, CreatedSlotState.getInstance());
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AvailableSlotState.getInstance());
  }
}
