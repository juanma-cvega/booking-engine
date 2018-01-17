package com.jusoft.bookingengine.usecase;

import com.google.common.collect.Iterables;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.jusoft.bookingengine.fixture.RoomFixtures.CREATE_ROOM_COMMAND;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomBuilder;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateRoomUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private SlotComponent slotComponent;

  @Autowired
  private CreateRoomUseCase createRoomUseCase;


  public CreateRoomUseCaseStepDefinitions() {
    When("^a room is created$", () ->
      roomCreated = createRoomUseCase.createRoom(CREATE_ROOM_COMMAND.apply(buildingCreated.getId())));
    Then("^the room should be stored", () -> {
      RoomView roomView = roomComponent.find(roomCreated.getId());
      assertThat(roomView.getId()).isEqualTo(roomCreated.getId());
      assertThat(roomView.getSlotDurationInMinutes()).isEqualTo(roomCreated.getSlotDurationInMinutes());
      assertThat(roomView.getSlotCreationConfigInfo()).isEqualTo(roomCreated.getSlotCreationConfigInfo());
      assertThat(roomView.getAvailableDays()).isEqualTo(roomCreated.getAvailableDays());
      assertThat(roomView.getOpenTimesPerDay()).isEqualTo(roomCreated.getOpenTimesPerDay());
      assertThat(roomView.getAuctionConfigInfo()).isEqualTo(roomCreated.getAuctionConfigInfo());
    });
    Then("^a notification of a created room should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(RoomCreatedEvent.class);
      RoomCreatedEvent roomCreatedEvent = (RoomCreatedEvent) messageCaptor.getValue();
      assertThat(roomCreatedEvent.getRoomId()).isEqualTo(roomCreated.getId());
      assertThat(roomCreatedEvent.getSlotDurationInMinutes()).isEqualTo(roomCreated.getSlotDurationInMinutes());
      assertThat(roomCreatedEvent.getAvailableDays()).isEqualTo(roomCreated.getAvailableDays());
      assertThat(roomCreatedEvent.getOpenTimesPerDay()).isEqualTo(roomCreated.getOpenTimesPerDay());
    });
    Given("^a room is to be created$", DataHolder::createRoomBuilder);
    Given("^the room is open between (.*) and (.*)$", (String startTime, String endTime) ->
      roomBuilder.openTimes.add(new OpenTime(LocalTime.parse(startTime), LocalTime.parse(endTime))));
    Given("^the room can open up to (.*) slots at the same time$", (Integer slots) ->
      roomBuilder.slotCreationConfigInfo = new MaxNumberOfSlotsStrategyConfigInfo(slots));
    Given("^the slots time is of (.*) minute$", (Integer slotDurationInMinutes) ->
      roomBuilder.slotDurationInMinutes = slotDurationInMinutes);
    Given("^the room has a (.*) minutes auction time and a (.*) days bookings created window$", (Integer auctionDuration, Integer daysRange) ->
      roomBuilder.auctionConfigInfo = new LessBookingsWithinPeriodConfigInfo(auctionDuration, daysRange));
    Given("^the room has a no auctions configuration$", () ->
      roomBuilder.auctionConfigInfo = new NoAuctionConfigInfo());
    When("^the room is created with that configuration$", () ->
      roomCreated = createRoomUseCase.createRoom(roomBuilder.build(buildingCreated.getId())));
    Then("^(.*) slots should have been created$", (Integer slotsToBeCreated) ->
      assertThat(slotComponent.findOpenSlotsFor(roomCreated.getId())).hasSize(slotsToBeCreated));
    Then("^the first slot should start at (.*)$", (String startTime) -> {
      SlotView slot = slotComponent.findOpenSlotsFor(roomCreated.getId()).get(0);
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start at (.*)$", (String startTime) -> {
      SlotView slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomCreated.getId()));
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start the day after$", () -> {
      SlotView slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomCreated.getId()));
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
    Then("^the first slot should start the day after$", () -> {
      SlotView slot = slotComponent.findOpenSlotsFor(roomCreated.getId()).get(0);
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
  }
}
