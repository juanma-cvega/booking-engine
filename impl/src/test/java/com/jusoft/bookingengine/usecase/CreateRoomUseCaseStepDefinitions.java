package com.jusoft.bookingengine.usecase;

import com.google.common.collect.Iterables;
import com.jusoft.bookingengine.component.auction.api.strategy.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.component.auction.api.strategy.NoAuctionConfigInfo;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.jusoft.bookingengine.fixture.RoomFixtures.CREATE_ROOM_COMMAND;
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
      roomHolder.roomCreated = roomComponent.create(CREATE_ROOM_COMMAND));
    Then("^the room should be stored", () -> {
      RoomView roomView = roomComponent.find(roomHolder.roomCreated.getId());
      assertThat(roomView.getId()).isEqualTo(roomHolder.roomCreated.getId());
      assertThat(roomView.getSlotDurationInMinutes()).isEqualTo(roomHolder.roomCreated.getSlotDurationInMinutes());
      assertThat(roomView.getMaxSlots()).isEqualTo(roomHolder.roomCreated.getMaxSlots());
      assertThat(roomView.getAvailableDays()).isEqualTo(roomHolder.roomCreated.getAvailableDays());
      assertThat(roomView.getOpenTimesPerDay()).isEqualTo(roomHolder.roomCreated.getOpenTimesPerDay());
      assertThat(roomView.getAuctionConfigInfo()).isEqualTo(roomHolder.roomCreated.getAuctionConfigInfo());
    });
    Then("^a notification of a created room should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(RoomCreatedEvent.class);
      RoomCreatedEvent roomCreatedEvent = (RoomCreatedEvent) messageCaptor.getValue();
      assertThat(roomCreatedEvent.getRoomId()).isEqualTo(roomHolder.roomCreated.getId());
      assertThat(roomCreatedEvent.getSlotDurationInMinutes()).isEqualTo(roomHolder.roomCreated.getSlotDurationInMinutes());
      assertThat(roomCreatedEvent.getMaxSlots()).isEqualTo(roomHolder.roomCreated.getMaxSlots());
      assertThat(roomCreatedEvent.getAvailableDays()).isEqualTo(roomHolder.roomCreated.getAvailableDays());
      assertThat(roomCreatedEvent.getOpenTimesPerDay()).isEqualTo(roomHolder.roomCreated.getOpenTimesPerDay());
    });
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
    Given("^the room has a no auctions configuration$", () ->
      roomHolder.roomBuilder.auctionConfigInfo = new NoAuctionConfigInfo());
    When("^the room is created with that configuration$", () -> {
      roomHolder.roomCreated = createRoomUseCase.createRoom(roomHolder.roomBuilder.build());
//      awaitSlotsCreation();
    });
    Then("^(.*) slots should have been created$", (Integer slotsToBeCreated) ->
      assertThat(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId())).hasSize(slotsToBeCreated));
    Then("^the first slot should start at (.*)$", (String startTime) -> {
      SlotView slot = slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).get(0);
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start at (.*)$", (String startTime) -> {
      SlotView slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()));
      LocalTime time = LocalTime.parse(startTime);
      assertThat(slot.getStartDate().toLocalTime()).isBetween(time, time);
    });
    Then("^the last slot should start the day after$", () -> {
      SlotView slot = Iterables.getLast(slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()));
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
    Then("^the first slot should start the day after$", () -> {
      SlotView slot = slotComponent.findOpenSlotsFor(roomHolder.roomCreated.getId()).get(0);
      LocalDate date = LocalDate.now(clock).plusDays(1);
      assertThat(slot.getStartDate().toLocalDate()).isAfterOrEqualTo(date).isBeforeOrEqualTo(date);
    });
  }
}
