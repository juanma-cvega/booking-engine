package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.PersonReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class NotifyOfSlotReservationUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private NotifyOfSlotReservationUseCase notifyOfSlotReservationUseCase;

  public NotifyOfSlotReservationUseCaseStepDefinitions() {
    When("^a notification of a slot reserved by a (.*) with id (\\d+) for slot (\\d+) is published$", (UserType userType, Long userId, Long slotId) ->
      notifyOfSlotReservationUseCase.notifyOfSlotReservation(slotId, SlotUser.of(userId, userType)));
    Then("^a notification of a slot reserved by a user with id (\\d+) for slot (\\d+) should be published$", (Long userId, Long slotId) -> {
      PersonReservationCreatedEvent event = verifyAndGetMessageOfType(PersonReservationCreatedEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotId);
      assertThat(event.getUserId()).isEqualTo(userId);
    });
    Then("^a notification of a slot reserved by a class with id (\\d+) for slot (\\d+) should be published$", (Long classId, Long slotId) -> {
      ClassReservationCreatedEvent event = verifyAndGetMessageOfType(ClassReservationCreatedEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotId);
      assertThat(event.getClassId()).isEqualTo(classId);
    });
  }
}
