package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotNotInAuctionException;
import org.junit.Test;

import java.time.Clock;

import static com.jusoft.bookingengine.component.slot.SlotState.State.AVAILABLE;
import static com.jusoft.bookingengine.component.slot.SlotState.State.IN_AUCTION;
import static com.jusoft.bookingengine.component.slot.SlotState.State.RESERVED;
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
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, null, OPEN_DATE, AVAILABLE))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_open_date_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, START_TIME, null, AVAILABLE))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void slot_without_state_fails_creation() {
    assertThatThrownBy(() -> new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void given_slot_state_is_available_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    assertThat(slot.isAvailable(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_and_current_time_is_before_start_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    assertThat(slot.isAvailable(Clock.fixed(START_TIME.toInstant().plus(1, HOURS), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_and_current_time_is_equal_to_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_reserved_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, RESERVED);
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_in_auction_and_current_time_is_within_start_and_end_time_is_available_should_return_false() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, IN_AUCTION);
    assertThat(slot.isAvailable(Clock.fixed(END_TIME.toInstant(), START_TIME.getZone()))).isFalse();
  }

  @Test
  public void given_slot_state_is_available_when_reserving_the_slot_it_should_change_state_to_reserved() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    Slot slotModified = slot.reserve(Clock.fixed(START_TIME.minusMinutes(10).toInstant(), START_TIME.getZone()));
    assertThat(slotModified.getState()).isEqualTo(RESERVED);
  }

  @Test
  public void given_slot_state_is_available_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AVAILABLE);
  }

  @Test
  public void given_slot_state_is_available_when_reserving_for_winner_it_should_throw_a_slot_not_in_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, AVAILABLE);
    assertThatThrownBy(slot::reserveForAuctionWinner)
      .isInstanceOf(SlotNotInAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_the_slot_it_should_throw_an_already_reserved_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, RESERVED);
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone())))
      .isInstanceOf(SlotAlreadyReservedException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_reserved_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, RESERVED);
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AVAILABLE);
  }

  @Test
  public void given_slot_state_is_reserved_when_reserving_for_winner_it_should_throw_a_slot_not_in_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, RESERVED);
    assertThatThrownBy(slot::reserveForAuctionWinner)
      .isInstanceOf(SlotNotInAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_in_auction_when_reserving_the_slot_it_should_throw_a_slot_pending_auction_exception() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, IN_AUCTION);
    assertThatThrownBy(() -> slot.reserve(Clock.fixed(START_TIME.toInstant(), START_TIME.getZone())))
      .isInstanceOf(SlotPendingAuctionException.class)
      .hasFieldOrPropertyWithValue("slotId", SLOT_ID_1);
  }

  @Test
  public void given_slot_state_is_in_auction_when_making_available_the_slot_it_should_change_state_to_available() {
    Slot slot = new Slot(SLOT_ID_1, ROOM_ID, START_TIME, OPEN_DATE, IN_AUCTION);
    Slot slotModified = slot.makeAvailable();
    assertThat(slotModified.getState()).isEqualTo(AVAILABLE);
  }
}
