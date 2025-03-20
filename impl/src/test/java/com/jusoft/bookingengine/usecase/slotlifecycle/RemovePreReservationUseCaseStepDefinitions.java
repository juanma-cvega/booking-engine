package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservationNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

public class RemovePreReservationUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private RemovePreReservationUseCase removePreReservationUseCase;

  @When("^the pre reservation for next (.*) at (.*) from zone (.*) is removed from room (\\d+)$")
  public void the_pre_reservation_for_next_at_from_zone_is_removed_from_room(DayOfWeek dayOfWeek, String slotTime, String zoneId, Long roomId) {
    removePreReservationUseCase.removePreReservationFrom(roomId, getNextDateFrom(slotTime, dayOfWeek, zoneId));
  }
  @Then("^a slot lifecycle manager for room (\\d+) should not contain a pre reservation for user (\\d+) next (.*) at (.*) from zone (.*)$")
  public void a_slot_lifecycle_manager_for_room_should_not_contain_a_pre_reservation_for_user_next_at_from_zone(Long roomId, Long userId, DayOfWeek dayOfWeek, String slotTime, String zoneId) {
    SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
    assertThat(managerView.getPreReservations()).doesNotContain(PreReservation.of(userId, getNextDateFrom(slotTime, dayOfWeek, zoneId)));
  };
  @When("^the pre reservation for next (.*) at (.*) from zone (.*) is tried to be removed from room (\\d+)$")
  public void the_pre_reservation_for_next_at_from_zone_is_tried_to_be_removed_from_room(DayOfWeek dayOfWeek, String slotTime, String zoneId, Long roomId) {
    storeException(() -> removePreReservationUseCase.removePreReservationFrom(roomId, getNextDateFrom(slotTime, dayOfWeek, zoneId)));
  }
  @Then("^the admin should receive a notification the slot lifecycle manager for room (\\d+) does not exist$")
  public void the_admin_should_receive_a_notification_the_slot_lifecycle_manager_for_room_does_not_exist(Long roomId) {
    SlotLifeCycleManagerNotFoundException exception = verifyAndGetExceptionThrown(SlotLifeCycleManagerNotFoundException.class);
    assertThat(exception.getRoomId()).isEqualTo(roomId);
  }
  @When("^the pre reservation for next (.*) at (.*) from zone (.*) is tried to be removed removed from room (\\d+)$")
  public void the_pre_reservation_for_next_at_from_zone_is_tried_to_be_removed_removed_from_room(DayOfWeek dayOfWeek, String slotTime, String zoneId, Long roomId) {
    storeException(() -> removePreReservationUseCase.removePreReservationFrom(roomId, getNextDateFrom(slotTime, dayOfWeek, zoneId)));
  }
  @Then("^the admin should receive a notification the pre reservation for next (.*) at (.*) from zone (.*) for slot lifecycle manager for room (\\d+) does not exist$")
  public void the_admin_should_receive_a_notification_the_pre_reservation_for_next_at_from_zone_for_slot_lifecycle_manager_for_room_does_not_exist(DayOfWeek dayOfWeek, String slotTime, String zoneId, Long roomId) {
    PreReservationNotFoundException exception = verifyAndGetExceptionThrown(PreReservationNotFoundException.class);
    assertThat(exception.getRoomId()).isEqualTo(roomId);
    assertThat(exception.getSlotStartTime()).isEqualTo(getNextDateFrom(slotTime, dayOfWeek, zoneId));
  }
}
