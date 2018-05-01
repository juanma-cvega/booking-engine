package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotPreReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class PreReserveSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  private static final String USER_TYPE = "userType";

  @Autowired
  private SlotManagerComponent slotManagerComponent;

  @Autowired
  private PreReserveSlotUseCase preReserveSlotUseCase;

  public PreReserveSlotUseCaseStepDefinitions() {
    When("^the slot is pre reserved for user (\\d+)$", (Long userId) ->
      preReserveSlotUseCase.preReserveSlot(slotCreated.getId(), userId, USER_TYPE));
    When("^the user (\\d+) tries to pre reserve the slot$", (Long userId) ->
      storeException(() -> preReserveSlotUseCase.preReserveSlot(slotCreated.getId(), userId, PERSON.name())));
    Then("^the slot should be pre reserved$", () -> {
      SlotView slot = slotManagerComponent.find(slotCreated.getId());
      assertThat(slot.getState()).isEqualTo(SlotState.PRE_RESERVED);
    });
    Then("^a notification of a slot pre reserved by user (\\d+) should be published$", (Long userId) -> {
      SlotPreReservedEvent event = verifyAndGetMessageOfType(SlotPreReservedEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
      assertThat(event.getSlotUser().getUserId()).isEqualTo(userId);
      assertThat(event.getSlotUser().getUserType()).isEqualTo(USER_TYPE);
    });
  }
}
