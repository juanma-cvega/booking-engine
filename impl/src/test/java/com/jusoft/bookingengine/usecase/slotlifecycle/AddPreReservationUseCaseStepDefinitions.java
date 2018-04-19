package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservationDateNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotAlreadyTakenException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPreReservationUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private Clock clock;
  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private AddPreReservationUseCase addPreReservationUseCase;

  public AddPreReservationUseCaseStepDefinitions(AddPreReservationUseCase addPreReservationUseCase) {
    When("^a pre reservation for next (.*) at (.*) from zone (.*) for user (.*) is created for room (.*)$", (DayOfWeek weekDay, String slotTime, String zoneId, Long userId, Long roomId) ->
      addPreReservationUseCase.addPreReservationTo(roomId, PreReservation.of(userId, getNextDateFrom(slotTime, weekDay, zoneId))));
    Then("^a slot lifecycle manager for room (\\d+) should contain a pre reservation for user (\\d+) next (.*) at (.*) from zone (.*)$", (Long roomId, Long userId, DayOfWeek dayOfWeek, String slotTime, String zoneId) -> {
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getPreReservations()).hasSize(1);
      PreReservation preReservation = managerView.getPreReservations().get(0);
      assertThat(preReservation.getReservationDate()).isEqualTo(getNextDateFrom(slotTime, dayOfWeek, zoneId));
      assertThat(preReservation.getUserId()).isEqualTo(userId);
    });
    When("^a pre reservation for next (.*) at (.*) from zone (.*) for user (.*) is tried to be created for room (\\d+)$", (DayOfWeek weekDay, String slotTime, String zoneId, Long userId, Long roomId) ->
      storeException(() -> addPreReservationUseCase.addPreReservationTo(roomId, PreReservation.of(userId, getNextDateFrom(slotTime, weekDay, zoneId)))));
    Then("^the admin should receive a notification the reservation is not valid for next (.*) at (.*) from zone (.*) for user (.*) for room (.*)$", (DayOfWeek dayOfWeek, String slotTime, String zoneId, Long userId, Long roomId) -> {
      ReservationDateNotValidException exception = verifyAndGetExceptionThrown(ReservationDateNotValidException.class);
      assertThat(exception.getReservationDate()).isEqualTo(getNextDateFrom(slotTime, dayOfWeek, zoneId));
      assertThat(exception.getRoomId()).isEqualTo(roomId);
    });
    Then("^the admin should receive a notification the reservation overlaps with another reservation for next (.*) at (.*) from zone (.*) for user (.*) for room (.*)$", (DayOfWeek dayOfWeek, String slotTime, String zoneId, Long userId, Long roomId) -> {
      SlotAlreadyTakenException exception = verifyAndGetExceptionThrown(SlotAlreadyTakenException.class);
      assertThat(exception.getReservationDate()).isEqualTo(getNextDateFrom(slotTime, dayOfWeek, zoneId));
    });
  }
}
