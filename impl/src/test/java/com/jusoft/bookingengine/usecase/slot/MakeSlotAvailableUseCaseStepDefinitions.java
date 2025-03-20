package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotMadeAvailableEvent;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class MakeSlotAvailableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotManagerComponent slotManagerComponent;

  @Autowired
  private MakeSlotAvailableUseCase makeSlotAvailableUseCase;

  @When("^the slot is made available$")
  public void the_slot_is_made_available() {
    makeSlotAvailableUseCase.makeSlotAvailable(slotCreated.getId());
  }
  @Then("^the slot should be available$")
  public void the_slot_should_be_available() {
    SlotView slot = slotManagerComponent.find(slotCreated.getId());
    assertThat(slot.getState()).isEqualTo(SlotState.AVAILABLE);
  };
  @Then("^a notification of a slot made available should be published$")
  public void a_notification_of_a_slot_made_available_should_be_published () {
    SlotMadeAvailableEvent event = verifyAndGetMessageOfType(SlotMadeAvailableEvent.class);
    assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
  }
}
