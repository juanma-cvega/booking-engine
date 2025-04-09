package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jusoft.bookingengine.fixture.SlotLifeCycleFixtures.SLOT_VALIDATION_INFO;
import static com.jusoft.bookingengine.holder.DataHolder.createSlotLifeCycleManagerBuilder;
import static com.jusoft.bookingengine.holder.DataHolder.slotLifeCycleManager;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSlotLifeCycleManagerUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private CreateSlotLifeCycleManagerUseCase createSlotLifeCycleManagerUseCase;

  @Given("^a slot lifecycle manager for room (\\d+) is to be created$")
  public void a_slot_lifecycle_manager_for_room_is_to_be_created(Long roomId) {
    DataHolder.createSlotLifeCycleManagerBuilder(roomId);
  }
  @Given("^the slot duration is (\\d+) minutes$")
  public void the_slot_duration_is_minutes(Integer slotDuration) {
    createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.slotDurationInMinutes = slotDuration;
  }
  @Given("^the slots are open$")
  public void the_slots_are_open(List<DayOfWeek> availableDays) {
    createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.availableDays = availableDays;
  }
  @Given("^the slots are open from (.*) to (.*)$")
  public void the_slots_are_open_from_to(String startOpenTime, String endOpenTime) {
    createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime, clock.getZone(), clock));
  }
  @Given("^the slot lifecycle is created with that configuration$")
  public void the_slot_lifecycle_is_created_with_that_configuration() {
    slotLifeCycleManager = createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(createSlotLifeCycleManagerBuilder.build());
  }
  @Given("^a slot lifecycle manager for room (\\d+) is created$")
  public void a_slot_lifecycle_manager_for_room_is_created(Long roomId) {
    slotLifeCycleManager = createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(CreateSlotLifeCycleManagerCommand.of(roomId, SLOT_VALIDATION_INFO));
  }
  @Then("^a slot lifecycle manager for room (.*) should be created$")
  public void a_slot_lifecycle_manager_for_room_should_be_created(Long roomId) {
    assertThat(slotLifeCycleManagerComponent.find(roomId)).isNotNull();
  }
}
