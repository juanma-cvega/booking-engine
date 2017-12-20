package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class SlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotComponent slotComponent;
  @Autowired
  private OpenNextSlotUseCase openNextSlotUseCase;


  public SlotUseCaseStepDefinitions() {
    When("^a slot is created$", () ->
      slotHolder.slotCreated = openNextSlotUseCase.openNextSlotFor(roomHolder.roomCreated.getId()));
    When("(.*) slots are created", (Integer slotsToCreate) ->
      IntStream.range(0, slotsToCreate).forEach((index) -> openNextSlotUseCase.openNextSlotFor(roomHolder.roomCreated.getId())));
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
    SlotView slotStored = slotComponent.find(slotHolder.slotCreated.getId(), slotHolder.slotCreated.getRoomId());
    assertThat(slotHolder.slotCreated.getId())
      .isEqualTo(slotStored.getId());
    assertThat(slotHolder.slotCreated.getRoomId())
      .isEqualTo(slotStored.getRoomId());
    assertThat(slotHolder.slotCreated.getStartDate())
      .isEqualTo(startDate)
      .isEqualTo(slotStored.getStartDate());
    assertThat(slotHolder.slotCreated.getEndDate())
      .isEqualTo(endDate)
      .isEqualTo(slotStored.getEndDate());
  }

  private void verifySlotCreatedEvent(ZonedDateTime startDate, ZonedDateTime endDate) {
    verify(messagePublisher).publish(messageCaptor.capture());
    assertThat(messageCaptor.getValue()).isInstanceOf(SlotCreatedEvent.class);
    SlotCreatedEvent slotCreatedEvent = (SlotCreatedEvent) messageCaptor.getValue();
    assertThat(slotHolder.slotCreated.getId())
      .isEqualTo(slotCreatedEvent.getSlotId());
    assertThat(slotHolder.slotCreated.getRoomId())
      .isEqualTo(slotCreatedEvent.getRoomId());
    assertThat(slotHolder.slotCreated.getStartDate())
      .isEqualTo(startDate)
      .isEqualTo(slotCreatedEvent.getStartDate());
    assertThat(slotHolder.slotCreated.getEndDate())
      .isEqualTo(endDate)
      .isEqualTo(slotCreatedEvent.getEndDate());
  }
}
