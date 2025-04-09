package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotManagerComponent slotManagerComponent;
  @Autowired
  private CreateSlotUseCase createSlotUseCase;

  @When("^a slot is created$")
  public void a_slot_is_created() {
    slotCreated = createSlotUseCase.createSlotFor(roomCreated.getId());
  }
  @When("^(.*) slots are created")
  public void slots_are_created(Integer slotsToCreate) {
    IntStream.range(0, slotsToCreate).forEach((index) -> createSlotUseCase.createSlotFor(roomCreated.getId()));
  }
  @Then("^a notification of a created slot starting at (.*) and ending at (.*) should be published$")
  public void a_notification_of_a_created_slot_starting_at_and_ending_at_should_be_published (String startTime, String endTime) {
    ZonedDateTime startDate = getDateFrom(startTime);
    ZonedDateTime endDate = getDateFrom(endTime);
    verifySlotCreatedEvent(startDate, endDate);
  }
  @Then("^a notification of a created slot starting the next day at (.*) and ending at (.*) should be published$")
  public void a_notification_of_a_created_slot_starting_the_next_day_at_and_ending_at_should_be_published (String startTime, String endTime) {
    ZonedDateTime startDate = getDateFrom(startTime, LocalDate.now(clock).plusDays(1));
    ZonedDateTime endDate = getDateFrom(endTime, LocalDate.now(clock).plusDays(1));
    verifySlotCreatedEvent(startDate, endDate);
  }
  @Then("^the slot should be stored starting at (.*) and ending at (.*)$")
  public void the_slot_should_be_stored_starting_at_and_ending_at (String startTime, String endTime) {
    ZonedDateTime startDate = getDateFrom(startTime);
    ZonedDateTime endDate = getDateFrom(endTime);
    verifySlotCreatedStored(startDate, endDate);
  }
  @Then("^the slot should be stored starting the next day at (.*) and ending at (.*)")
  public void the_slot_should_be_stored_starting_the_next_day_at_and_ending_at (String startTime, String endTime){
    ZonedDateTime startDate = getDateFrom(startTime, LocalDate.now(clock).plusDays(1));
    ZonedDateTime endDate = getDateFrom(endTime, LocalDate.now(clock).plusDays(1));
    verifySlotCreatedStored(startDate, endDate);
  }

  private void verifySlotCreatedStored(ZonedDateTime startDate, ZonedDateTime endDate) {
    SlotView slotStored = slotManagerComponent.find(slotCreated.getId());
    assertThat(slotCreated.getId())
      .isEqualTo(slotStored.getId());
    assertThat(slotCreated.getRoomId())
      .isEqualTo(slotStored.getRoomId());
    assertThat(slotCreated.getOpenDate().getStartTime())
      .isEqualTo(startDate)
      .isEqualTo(slotStored.getOpenDate().getStartTime());
    assertThat(slotCreated.getOpenDate().getEndTime())
      .isEqualTo(endDate)
      .isEqualTo(slotStored.getOpenDate().getEndTime());
  }

  private void verifySlotCreatedEvent(ZonedDateTime startDate, ZonedDateTime endDate) {
    verify(messagePublisher).publish(messageCaptor.capture());
    assertThat(messageCaptor.getValue()).isInstanceOf(SlotCreatedEvent.class);
    SlotCreatedEvent slotCreatedEvent = (SlotCreatedEvent) messageCaptor.getValue();
    assertThat(slotCreated.getId())
      .isEqualTo(slotCreatedEvent.getSlotId());
    assertThat(slotCreated.getRoomId())
      .isEqualTo(slotCreatedEvent.getRoomId());
    assertThat(slotCreated.getOpenDate().getStartTime())
      .isEqualTo(startDate)
      .isEqualTo(slotCreatedEvent.getOpenDate().getStartTime());
    assertThat(slotCreated.getOpenDate().getEndTime())
      .isEqualTo(endDate)
      .isEqualTo(slotCreatedEvent.getOpenDate().getEndTime());
  }
}
