package com.jusoft.bookingengine.component.room;

import com.google.common.collect.Iterables;
import com.jusoft.bookingengine.component.AbstractStepDefinitions;
import com.jusoft.bookingengine.component.auction.api.strategy.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.component.mock.ClockStub;
import com.jusoft.bookingengine.component.mock.MessagesSink;
import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.component.shared.Message;
import com.jusoft.bookingengine.component.slot.Slot;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.usecase.RoomUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.jusoft.bookingengine.component.room.RoomFixtures.CREATE_ROOM_COMMAND;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RoomManagementStepDefinitions extends AbstractStepDefinitions {

  private static final long TIMEOUT = 10000;
  private static final int WAITING_TIME = 100;

  @Autowired
  private RoomUseCase roomUseCase;

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
      roomHolder.roomCreated = roomUseCase.createRoom(CREATE_ROOM_COMMAND));
    When("^the room is retrieved", () ->
      roomHolder.roomFetched = roomComponent.find(roomHolder.roomCreated.getId()));
    Then("^the user should see the room created", () ->
      assertThat(roomHolder.roomFetched.getId()).isEqualTo(roomHolder.roomCreated.getId()));
    Given("^a room is to be created$", () ->
      roomHolder.createRoomBuilder());
    Given("^the room is open between (.*) and (.*)$", (String startTime, String endTime) ->
      roomHolder.roomBuilder.openTimes.add(new OpenTime(LocalTime.parse(startTime), LocalTime.parse(endTime))));
    Given("^the room can open up to (.*) slots at the same time$", (Integer slots) ->
      roomHolder.roomBuilder.maxSlots = slots);
    Given("^the slots time is of (.*) minute$", (Integer slotDurationInMinutes) ->
      roomHolder.roomBuilder.slotDurationInMinutes = slotDurationInMinutes);
    Given("^the room has a (.*) minutes auction time and a (.*) days bookings created window$", (Integer auctionDuration, Integer daysRange) ->
      roomHolder.roomBuilder.auctionConfigInfo = new LessBookingsWithinPeriodConfigInfo(auctionDuration, daysRange));
    When("^the room is created with that configuration$", () -> {
      roomHolder.roomCreated = roomUseCase.createRoom(roomHolder.roomBuilder.build());
      awaitSlotsCreation();
    });
    Then("^(.*) slots should have been created$", (Integer slotsToBeCreated) -> {
      assertThat(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId())).hasSize(slotsToBeCreated);
    });
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
    Then("^a new slot should be scheduled to be created at (.*)$", (String scheduledTime) -> {
      List<ScheduledTask> openNextCommandTasks = getWaitingScheduledEventsOfType(OpenNextSlotCommand.class);
      assertThat(openNextCommandTasks).hasSize(1);
      ZonedDateTime scheduledZonedDateTime = ZonedDateTime.of(LocalDate.now(clock), LocalTime.parse(scheduledTime), clock.getZone());
      assertThat(openNextCommandTasks.get(0).getScheduledEvent().getExecutionTime()).isEqualTo(scheduledZonedDateTime);
    });
    Then("^a new slot should be scheduled to be created next day at (.*)", (String scheduledTime) -> {
      List<ScheduledTask> openNextCommandTasks = getWaitingScheduledEventsOfType(OpenNextSlotCommand.class);
      assertThat(openNextCommandTasks).hasSize(1);
      ZonedDateTime scheduledZonedDateTime = ZonedDateTime.of(LocalDate.now(clock).plusDays(1), LocalTime.parse(scheduledTime), clock.getZone());
      assertThat(openNextCommandTasks.get(0).getScheduledEvent().getExecutionTime()).isEqualTo(scheduledZonedDateTime);
    });
  }

  private List<ScheduledTask> getWaitingScheduledEventsOfType(Class<? extends Message> clazz) {
    return scheduledTasksExecutor.getScheduledTasks().stream()
      .filter(scheduledTask -> !scheduledTask.getTask().isDone())
      .filter(scheduledTask -> scheduledTask.getScheduledEvent().getMessage().getClass().equals(clazz))
      .collect(Collectors.toList());
  }

  private void awaitSlotsCreation() {
    int maxSlots = roomHolder.roomCreated.getMaxSlots();
    long startTime = System.currentTimeMillis();
    while (isSlotsCreatedForRoomLessThanMaxSlots(maxSlots, startTime)) {
      try {
        Thread.sleep(WAITING_TIME);
      } catch (InterruptedException e) {
        log.error("Unable to wait for messages", e);
      }
    }
  }

  private boolean isSlotsCreatedForRoomLessThanMaxSlots(int maxSlots, long startTime) {
    return messagesSink.getMessages(SlotCreatedEvent.class).stream()
      .filter(slotCreatedEvent -> slotCreatedEvent.getRoomId() == roomHolder.roomCreated.getId())
      .count() < maxSlots || System.currentTimeMillis() > startTime + TIMEOUT;
  }

}
