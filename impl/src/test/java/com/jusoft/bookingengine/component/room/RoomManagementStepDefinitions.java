package com.jusoft.bookingengine.component.room;

import com.google.common.collect.Iterables;
import com.jusoft.bookingengine.component.AbstractStepDefinitions;
import com.jusoft.bookingengine.component.mock.ClockStub;
import com.jusoft.bookingengine.component.mock.MessagesSink;
import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.shared.Message;
import com.jusoft.bookingengine.component.slot.Slot;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.jusoft.bookingengine.component.room.RoomFixtures.CREATE_ROOM_COMMAND;
import static com.jusoft.bookingengine.component.timer.TimeConstants.UTC;
import static org.assertj.core.api.Assertions.assertThat;

public class RoomManagementStepDefinitions extends AbstractStepDefinitions {

  @Autowired
  private RoomComponent roomComponent;

  @Autowired
  private SlotComponent slotComponent;

  @Autowired
  private RoomHolder roomHolder;

  @Autowired
  private ClockStub clock;

  @Autowired
  private MessagesSink messagesSink;

  public RoomManagementStepDefinitions() {
    Given("^a room is created$", () ->
      roomHolder.roomCreated = roomComponent.create(CREATE_ROOM_COMMAND));
    When("^the room is retrieved", () ->
      roomHolder.roomFetched = roomComponent.find(roomHolder.roomCreated.getId()));
    Then("^the user should see the room created", () ->
      assertThat(roomHolder.roomFetched.getId()).isEqualTo(roomHolder.roomCreated.getId()));
    Given("^a room is to be created$", () ->
      roomHolder.createRoomBuilder());
    Given("^the room is open between (.*) and (.*)$", (String startTime, String endTime) ->
      roomHolder.roomBuilder.openTimes.add(new OpenTime(LocalTime.parse(startTime), LocalTime.parse(endTime))));
    Given("^current time is (.*)$", (String currentTime) ->
      clock.setClock(getFixedClockAt(currentTime)));
    Given("^the room can open up to (.*) slots at the same time$", (Integer slots) ->
      roomHolder.roomBuilder.maxSlots = slots);
    Given("^the slots time is of (.*) minute$", (Integer slotDurationInMinutes) ->
      roomHolder.roomBuilder.slotDurationInMinutes = slotDurationInMinutes);
    When("^the room is created with that configuration$", () ->
      roomHolder.roomCreated = roomComponent.create(roomHolder.roomBuilder.build()));
    Then("^(.*) slots should have been created$", (Integer slotsToBeCreated) ->
      assertThat(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId())).hasSize(slotsToBeCreated));
    Then("^the first slot should start at (.*)$", (String startTime) -> {
      Slot slot = slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).get(0);
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start at (.*)$", (String startTime) -> {
      Slot slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()));
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start the day after$", () -> {
      Slot slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()));
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
    Then("^the first slot should start the day after$", () -> {
      Slot slot = slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).get(0);
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
    Then("^a new slot should be scheduled to be created at (.*)$", (String scheduleTime) -> {
      List<Message> messagesSent = messagesSink.pollMessages();
      assertThat(messagesSent).hasSize(1);
      assertThat(messagesSent.get(0)).isInstanceOf(OpenNextSlotCommand.class);
    });
  }

  private Clock getFixedClockAt(String localTime) {
    LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(localTime));
    return Clock.fixed(currentDateTime.toInstant(ZoneOffset.UTC), UTC);
  }
}
