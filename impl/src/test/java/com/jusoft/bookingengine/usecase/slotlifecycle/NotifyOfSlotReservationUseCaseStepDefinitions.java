package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.PersonReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class NotifyOfSlotReservationUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private NotifyOfSlotReservationUseCase notifyOfSlotReservationUseCase;

  @When("^a notification of a slot reserved by a (.*) with id (\\d+) for slot (\\d+) is published$")
  public void a_notification_of_a_slot_reserved_by_a_with_id_for_slot_is_published(UserType userType, Long userId, Long slotId) {
    notifyOfSlotReservationUseCase.notifyOfSlotReservation(slotId, SlotUser.of(userId, userType));
  }
  @Then("^a notification of a slot reserved by a user with id (\\d+) for slot (\\d+) should be published$")
  public void a_notification_of_a_slot_reserved_by_a_user_with_id_for_slot_should_be_published(Long userId, Long slotId) {
    PersonReservationCreatedEvent event = verifyAndGetMessageOfType(PersonReservationCreatedEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotId);
    assertThat(event.getUserId()).isEqualTo(userId);
  };
  @Then("^a notification of a slot reserved by a class with id (\\d+) for slot (\\d+) should be published$")
  public void a_notification_of_a_slot_reserved_by_a_class_with_id_for_slot_should_be_published(Long classId, Long slotId) {
    ClassReservationCreatedEvent event = verifyAndGetMessageOfType(ClassReservationCreatedEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotId);
    assertThat(event.getClassId()).isEqualTo(classId);
  }
}
