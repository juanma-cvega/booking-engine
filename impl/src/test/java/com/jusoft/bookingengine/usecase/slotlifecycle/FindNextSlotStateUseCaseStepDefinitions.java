package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresPreReservationEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotCanBeMadeAvailableEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresAuctionEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.CLASS;
import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;
import static org.assertj.core.api.Assertions.assertThat;

public class FindNextSlotStateUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private FindNextSlotStateUseCase findNextSlotStateUseCase;

  @When("^slot (\\d+) from room (.*) started the (.*) at (.*) has just been created$")
  public void slot_from_room_started_the_at_has_just_been_created(Long slotId, Long roomId, String slotStartDate, String slotStartTime) {
    findNextSlotStateUseCase.findNextSlotStateUseCase(slotId, roomId, getDateFrom(slotStartTime, slotStartDate));
  }
  @When("^slot (\\d+) from room (.*) starts the next (.*) at (.*) from zone (.*) has just been created$")
  public void slot_from_room_starts_the_next_at_from_zone_has_just_been_created(Long slotId, Long roomId, DayOfWeek dayOfWeek, String slotStartTime, String zoneId) {
    findNextSlotStateUseCase.findNextSlotStateUseCase(slotId, roomId, getNextDateFrom(slotStartTime, dayOfWeek, zoneId));
  }
  @Then("^a notification that the slot (\\d+) is available should be published$")
  public void a_notification_that_the_slot_is_available_should_be_published(Long slotId) {
    SlotCanBeMadeAvailableEvent event = verifyAndGetMessageOfType(SlotCanBeMadeAvailableEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotId);
  }
  @Then("^a notification that the slot (\\d+) requires an auction of (.*) minutes duration and bookings period of (.*) days should be published$")
  public void a_notification_that_the_slot_requires_an_auction_of_minutes_duration_and_bookings_period_of_days_should_be_published(Long slotId, Integer auctionDuration, Integer periodValue) {
    SlotRequiresAuctionEvent event = verifyAndGetMessageOfType(SlotRequiresAuctionEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotId);
    assertThat(event.getAuctionConfigInfo()).isEqualTo(LessBookingsWithinPeriodConfigInfo.of(auctionDuration, periodValue));
  }
  @Then("^a notification that the slot (\\d+) is pre reserved for user (\\d+) should be published$")
  public void a_notification_that_the_slot_is_pre_reserved_for_user_should_be_published(Long slotId, Long userId) {
    SlotRequiresPreReservationEvent event = verifyAndGetMessageOfType(SlotRequiresPreReservationEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotId);
    assertThat(event.getSlotUser().getId()).isEqualTo(userId);
    assertThat(event.getSlotUser().getUserType()).isEqualTo(PERSON);
  }
  @Then("^a notification that the slot (\\d+) is reserved for class (\\d+) should be published$")
  public void a_notification_that_the_slot_is_reserved_for_class_should_be_published(Long slotId, Long classId) {
    SlotRequiresPreReservationEvent event = verifyAndGetMessageOfType(SlotRequiresPreReservationEvent.class);
    assertThat(event.getSlotUser().getId()).isEqualTo(classId);
    assertThat(event.getSlotUser().getUserType()).isEqualTo(CLASS);
    assertThat(event.getSlotId()).isEqualTo(slotId);
  }
}
