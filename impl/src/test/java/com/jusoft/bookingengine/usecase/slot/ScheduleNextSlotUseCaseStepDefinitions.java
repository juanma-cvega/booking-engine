package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.room.api.SlotRequiredEvent;
import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class ScheduleNextSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ScheduleNextSlotUseCase scheduleNextSlotUseCase;
  @Autowired
  private List<ScheduledTask> tasks;

  @When("^a slot is scheduled$")
  public void a_slot_is_scheduled() throws Throwable {
    scheduleNextSlotUseCase.scheduleNextSlot(roomCreated.getId());
  }
  @Then("^a command to open the next slot for the room should be published immediately$")
  public void a_command_to_open_the_next_slot_for_the_room_should_be_published_immediately() {
    await().atMost(1, SECONDS).untilAsserted(() -> {
      SlotRequiredEvent event = verifyAndGetMessageOfType(SlotRequiredEvent.class);
      assertThat(event.getRoomId()).isEqualTo(roomCreated.getId());
    });
  }
  @Then("^a command to open the next slot for the room should be scheduled to be published at (.*)$")
  public void a_command_to_open_the_next_slot_for_the_room_should_be_scheduled_to_be_published_at(String localTime) {
    assertThat(tasks).hasSize(1);
    assertThat(tasks.get(0).getExecutionTime()).isEqualTo(getDateFrom(localTime));
    assertThat(tasks.get(0).getScheduledEvent()).isEqualTo(SlotRequiredEvent.of(roomCreated.getId()));
    assertThat(tasks.get(0).getTask().isDone()).isFalse();
  }
}
