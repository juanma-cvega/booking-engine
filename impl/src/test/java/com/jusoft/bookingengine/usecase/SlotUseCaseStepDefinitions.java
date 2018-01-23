package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class SlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotComponent slotComponent;
  @Autowired
  private OpenNextSlotUseCase openNextSlotUseCase;


  public SlotUseCaseStepDefinitions() {
    When("^a slot is created$", () ->
      slotCreated = openNextSlotUseCase.openNextSlotFor(new OpenNextSlotCommand(roomCreated.getId(), clubCreated.getId())));
    When("(.*) slots are created", (Integer slotsToCreate) ->
      IntStream.range(0, slotsToCreate).forEach((index) -> openNextSlotUseCase.openNextSlotFor(new OpenNextSlotCommand(roomCreated.getId(), clubCreated.getId()))));
    Then("^a notification of a created slot starting at (.*) and ending at (.*) should be published$", (String startTime, String endTime) -> {
      ZonedDateTime startDate = getDateFrom(startTime);
      ZonedDateTime endDate = getDateFrom(endTime);
      verifySlotCreatedEvent(startDate, endDate);
    });
    Then("^a notification of a created slot starting the next day at (.*) and ending at (.*) should be published$", (String startTime, String endTime) -> {
      ZonedDateTime startDate = getDateFrom(startTime, LocalDate.now(clock).plusDays(1));
      ZonedDateTime endDate = getDateFrom(endTime, LocalDate.now(clock).plusDays(1));
      verifySlotCreatedEvent(startDate, endDate);
    });
    Then("^the slot should be stored starting at (.*) and ending at (.*)$", (String startTime, String endTime) -> {
      ZonedDateTime startDate = getDateFrom(startTime);
      ZonedDateTime endDate = getDateFrom(endTime);
      verifySlotCreatedStored(startDate, endDate);
    });
    Then("^the slot should be stored starting the next day at (.*) and ending at (.*)", (String startTime, String endTime) -> {
      ZonedDateTime startDate = getDateFrom(startTime, LocalDate.now(clock).plusDays(1));
      ZonedDateTime endDate = getDateFrom(endTime, LocalDate.now(clock).plusDays(1));
      verifySlotCreatedStored(startDate, endDate);
    });
  }

  private void verifySlotCreatedStored(ZonedDateTime startDate, ZonedDateTime endDate) {
    SlotView slotStored = slotComponent.find(slotCreated.getId());
    assertThat(slotCreated.getId())
      .isEqualTo(slotStored.getId());
    assertThat(slotCreated.getRoomId())
      .isEqualTo(slotStored.getRoomId());
    assertThat(slotCreated.getStartDate())
      .isEqualTo(startDate)
      .isEqualTo(slotStored.getStartDate());
    assertThat(slotCreated.getEndDate())
      .isEqualTo(endDate)
      .isEqualTo(slotStored.getEndDate());
  }

  private void verifySlotCreatedEvent(ZonedDateTime startDate, ZonedDateTime endDate) {
    verify(messagePublisher).publish(messageCaptor.capture());
    assertThat(messageCaptor.getValue()).isInstanceOf(SlotCreatedEvent.class);
    SlotCreatedEvent slotCreatedEvent = (SlotCreatedEvent) messageCaptor.getValue();
    assertThat(slotCreated.getId())
      .isEqualTo(slotCreatedEvent.getSlotId());
    assertThat(slotCreated.getRoomId())
      .isEqualTo(slotCreatedEvent.getRoomId());
    assertThat(slotCreated.getStartDate())
      .isEqualTo(startDate)
      .isEqualTo(slotCreatedEvent.getStartDate());
    assertThat(slotCreated.getEndDate())
      .isEqualTo(endDate)
      .isEqualTo(slotCreatedEvent.getEndDate());
  }
}
