package com.jusoft.component.slot;

import com.jusoft.component.AbstractStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.slot.SlotsFixtures.CREATE_SLOT_COMMAND;
import static com.jusoft.component.slot.SlotsFixtures.createSlotRequestWith;
import static org.assertj.core.api.Assertions.assertThat;

public class SlotManagementStepDefinitions extends AbstractStepDefinitions {

    private static final Long ANY_SLOT_ID = 44444L;

    @Autowired
    private SlotComponent slotComponent;

    @Autowired
    private SlotHolder slotHolder;

    private RuntimeException exceptionThrown;

    public SlotManagementStepDefinitions() {

        Given("^a slot is created$", () -> slotHolder.slotCreated = slotComponent.create(CREATE_SLOT_COMMAND));
        When("^the slot is retrieved$", () -> slotHolder.slotFetched = slotComponent.find(slotHolder.slotCreated.getSlotId(), slotHolder.slotCreated.getRoomId()));
        Then("^the user should see the slot created$", () -> assertThat(slotHolder.slotFetched).isEqualTo(slotHolder.slotCreated));
        Given("^a slot is created for room (\\d+)$", (Long roomId) -> slotHolder.slotsCreated.add(slotComponent.create(createSlotRequestWith(roomId))));
        When("^the list of slots is fetched for room (\\d+)$", (Long roomId) -> slotHolder.slotsFetched = slotComponent.getSlotsFor(roomId));
        Then("^the list should contain the created rooms$", () -> assertThat(slotHolder.slotsFetched).hasSameElementsAs(slotHolder.slotsCreated));
        When("^a non existent slot is retrieved$", () -> storeException(() -> slotComponent.find(ANY_SLOT_ID, ROOM_ID)));
        Then("^the user should be notified the slot does not exist$", () -> assertThat(exceptionThrown).isNotNull().isInstanceOf(SlotNotFoundException.class));
    }

    private void storeException(Runnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException exception) {
            exceptionThrown = exception;
        }
    }
}