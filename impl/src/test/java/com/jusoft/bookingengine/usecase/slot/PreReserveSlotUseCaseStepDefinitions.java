package com.jusoft.bookingengine.usecase.slot;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotPreReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class PreReserveSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    private static final String USER_TYPE = "userType";

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Autowired private PreReserveSlotUseCase preReserveSlotUseCase;

    @When("^the slot is pre reserved for user (\\d+)$")
    public void the_slot_is_pre_reserved_for_user(Long userId) {
        preReserveSlotUseCase.preReserveSlot(slotCreated.id(), userId, USER_TYPE);
    }

    @When("^the user (\\d+) tries to pre reserve the slot$")
    public void the_user_tries_to_pre_reserve_the_slot(Long userId) {
        storeException(
                () ->
                        preReserveSlotUseCase.preReserveSlot(
                                slotCreated.id(), userId, PERSON.name()));
    }

    @Then("^the slot should be pre reserved$")
    public void the_slot_should_be_pre_reserved() {
        SlotView slot = slotManagerComponent.find(slotCreated.id());
        assertThat(slot.state()).isEqualTo(SlotState.PRE_RESERVED);
    }

    @Then("^a notification of a slot pre reserved by user (\\d+) should be published$")
    public void a_notification_of_a_slot_pre_reserved_by_user_should_be_published(Long userId) {
        SlotPreReservedEvent event = verifyAndGetMessageOfType(SlotPreReservedEvent.class);
        assertThat(event.slotId()).isEqualTo(slotCreated.id());
        assertThat(event.slotUser().userId()).isEqualTo(userId);
        assertThat(event.slotUser().userType()).isEqualTo(USER_TYPE);
    }
}
