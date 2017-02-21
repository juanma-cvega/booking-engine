package com.jusoft.slot;

import com.jusoft.AbstractStepDefinitions;
import com.jusoft.holder.SlotHolder;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.fixtures.SlotsFixtures.CREATE_SLOT_REQUEST;
import static com.jusoft.fixtures.SlotsFixtures.createSlotRequestWith;
import static org.assertj.core.api.Assertions.assertThat;

public class SlotManagementStepDefinitions extends AbstractStepDefinitions {

    @Autowired
    private SlotComponent slotComponentInProcess;

    @Autowired
    private SlotHolder slotHolder;

    public SlotManagementStepDefinitions() {

        Given("^a slot is created$", () -> slotHolder.slotCreated = slotComponentInProcess.create(CREATE_SLOT_REQUEST));
        When("^the slot is retrieved$", () -> slotHolder.slotFetched = slotComponentInProcess.find(slotHolder.slotCreated.getSlotId(), slotHolder.slotCreated.getRoomId()));
        Then("^the slot fetched and the slot created should be the same$", () -> assertThat(slotHolder.slotFetched).isEqualTo(slotHolder.slotCreated));
        Given("^a slot is created for room (\\d+)$", (Long roomId) -> slotHolder.slotsCreated.add(slotComponentInProcess.create(createSlotRequestWith(roomId))));
        When("^the list of slots is fetched for room (\\d+)$", (Long roomId) -> slotHolder.slotsFetched = slotComponentInProcess.getSlotsFor(roomId));
        Then("^the list should contain the created rooms$", () -> assertThat(slotHolder.slotsFetched).hasSameElementsAs(slotHolder.slotsCreated));
    }
}
