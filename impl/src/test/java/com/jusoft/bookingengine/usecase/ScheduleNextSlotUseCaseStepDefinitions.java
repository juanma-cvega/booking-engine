package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class ScheduleNextSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ScheduleNextSlotUseCase scheduleNextSlotUseCase;

  public ScheduleNextSlotUseCaseStepDefinitions() {
    When("^a slot is scheduled$", () ->
      scheduleNextSlotUseCase.scheduleNextSlot(roomHolder.roomCreated.getId()));
    Then("^a notification of a scheduled slot creation to be executed immediately should be published$", () ->
      verifyScheduledEvent(ZonedDateTime.now(clock)));
    Then("^a notification of a scheduled slot creation to be executed at (.*) should be published$", (String scheduleDate) ->
      verifyScheduledEvent(getDateFrom(scheduleDate)));
  }

  private void verifyScheduledEvent(ZonedDateTime scheduleDate) {
    verify(messagePublisher).publish(messageCaptor.capture());
    assertThat(messageCaptor.getValue()).isInstanceOf(ScheduledEvent.class);
    ScheduledEvent scheduledEvent = (ScheduledEvent) messageCaptor.getValue();
    assertThat(scheduledEvent.getExecutionTime()).isEqualTo(scheduleDate);
    assertThat(scheduledEvent.getMessage()).isInstanceOf(OpenNextSlotCommand.class);
    OpenNextSlotCommand openNextSlotCommand = (OpenNextSlotCommand) scheduledEvent.getMessage();
    assertThat(openNextSlotCommand.getRoomId()).isEqualTo(roomHolder.roomCreated.getId());
  }
}
