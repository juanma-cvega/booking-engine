package com.jusoft.bookingengine.usecase.room;

import static com.jusoft.bookingengine.fixture.RoomFixtures.CREATE_ROOM_COMMAND;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.roomBuilder;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.google.common.collect.Iterables;
import com.jusoft.bookingengine.component.building.api.BuildingNotFoundException;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateRoomUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    private static final long NON_EXISTING_BUILDING_ID = 5667L;

    @Autowired private RoomManagerComponent roomManagerComponent;

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Autowired private CreateRoomUseCase createRoomUseCase;

    @When("^a room is created$")
    public void a_room_is_created() {
        roomCreated = createRoomUseCase.createRoom(CREATE_ROOM_COMMAND.apply(buildingCreated.id()));
    }

    @Then("^the room should be stored")
    public void the_room_should_be_stored() {
        RoomView roomView = roomManagerComponent.find(roomCreated.id());
        assertThat(roomView.id()).isEqualTo(roomCreated.id());
        assertThat(roomView.buildingId()).isEqualTo(roomCreated.buildingId());
        assertThat(roomView.slotDurationInMinutes()).isEqualTo(roomCreated.slotDurationInMinutes());
        assertThat(roomView.slotCreationConfigInfo())
                .isEqualTo(roomCreated.slotCreationConfigInfo());
        assertThat(roomView.availableDays()).isEqualTo(roomCreated.availableDays());
        assertThat(roomView.openTimesPerDay()).isEqualTo(roomCreated.openTimesPerDay());
    }

    @Then("^a notification of a created room should be published$")
    public void a_notification_of_a_created_room_should_be_published() {
        verify(messagePublisher).publish(messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isInstanceOf(RoomCreatedEvent.class);
        RoomCreatedEvent roomCreatedEvent = (RoomCreatedEvent) messageCaptor.getValue();
        assertThat(roomCreatedEvent.roomId()).isEqualTo(roomCreated.id());
        assertThat(roomCreatedEvent.slotDurationInMinutes())
                .isEqualTo(roomCreated.slotDurationInMinutes());
        assertThat(roomCreatedEvent.availableDays()).isEqualTo(roomCreated.availableDays());
        assertThat(roomCreatedEvent.openTimesPerDay()).isEqualTo(roomCreated.openTimesPerDay());
    }
    ;

    @Given("^a room is to be created$")
    public void a_room_is_to_be_createdDataHolder() {
        DataHolder.createRoomBuilder();
    }

    @Given("^the room is open between (.*) and (.*)$")
    public void the_room_is_open_between_and(String startTime, String endTime) {
        roomBuilder.openTimes.add(OpenTime.of(startTime, endTime, clock.getZone(), clock));
    }

    @Given("^the room can open up to (.*) slots at the same time$")
    public void the_room_can_open_up_to_slots_at_the_same_time(Integer slots) {
        roomBuilder.slotCreationConfigInfo = new MaxNumberOfSlotsStrategyConfigInfo(slots);
    }

    @Given("^the slots time is of (.*) minute$")
    public void the_slots_time_is_of_minute(Integer slotDurationInMinutes) {
        roomBuilder.slotDurationInMinutes = slotDurationInMinutes;
    }

    @When("^the room is created with that configuration$")
    public void the_room_is_created_with_that_configuration() {
        roomCreated = createRoomUseCase.createRoom(roomBuilder.build(buildingCreated.id()));
    }

    @Then("^(.*) slots should have been created$")
    public void slots_should_have_been_created(Integer slotsToBeCreated) {
        assertThat(slotManagerComponent.findOpenSlotsFor(roomCreated.id()))
                .hasSize(slotsToBeCreated);
    }

    @Then("^the first slot should start at (.*)$")
    public void the_first_slot_should_start_at(String startTime) {
        SlotView slot = slotManagerComponent.findOpenSlotsFor(roomCreated.id()).get(0);
        LocalTime time = LocalTime.parse(startTime);
        assertThat(slot.openDate().getStartTime().toLocalTime()).isBetween(time, time);
    }

    @Then("^the last slot should start at (.*)$")
    public void the_last_slot_should_start_at(String startTime) {
        SlotView slot = Iterables.getLast(slotManagerComponent.findOpenSlotsFor(roomCreated.id()));
        LocalTime time = LocalTime.parse(startTime);
        assertThat(slot.openDate().getStartTime().toLocalTime()).isBetween(time, time);
    }

    @Then("^the last slot should start the day after$")
    public void the_last_slot_should_start_the_day_after() {
        SlotView slot = Iterables.getLast(slotManagerComponent.findOpenSlotsFor(roomCreated.id()));
        LocalDate date = LocalDate.now(clock).plusDays(1);
        assertThat(slot.openDate().getStartTime().toLocalDate())
                .isAfterOrEqualTo(date)
                .isBeforeOrEqualTo(date);
    }
    ;

    @Then("^the first slot should start the day after$")
    public void the_first_slot_should_start_the_day_after() {
        SlotView slot = slotManagerComponent.findOpenSlotsFor(roomCreated.id()).get(0);
        LocalDate date = LocalDate.now(clock).plusDays(1);
        assertThat(slot.openDate().getStartTime().toLocalDate())
                .isAfterOrEqualTo(date)
                .isBeforeOrEqualTo(date);
    }

    @When("^a room is created for a non existing building$")
    public void a_room_is_created_for_a_non_existing_building() {
        storeException(
                () ->
                        createRoomUseCase.createRoom(
                                CREATE_ROOM_COMMAND.apply(NON_EXISTING_BUILDING_ID)));
    }

    @Then("^the user should be notified the building does not exist$")
    public void the_user_should_be_notified_the_building_does_not_exist() {
        assertThat(exceptionThrown).isInstanceOf(BuildingNotFoundException.class);
        BuildingNotFoundException exception = (BuildingNotFoundException) exceptionThrown;
        assertThat(exception.getBuildingId()).isEqualTo(NON_EXISTING_BUILDING_ID);
    }
}
