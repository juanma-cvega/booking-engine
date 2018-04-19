package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

import static com.jusoft.bookingengine.fixture.SlotLifeCycleFixtures.SLOT_VALIDATION_INFO;
import static com.jusoft.bookingengine.holder.DataHolder.createSlotLifeCycleManagerBuilder;
import static com.jusoft.bookingengine.holder.DataHolder.slotLifeCycleManager;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSlotLifeCycleManagerUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private CreateSlotLifeCycleManagerUseCase createSlotLifeCycleManagerUseCase;

  public CreateSlotLifeCycleManagerUseCaseStepDefinitions() {
    Given("^a slot lifecycle manager for room (\\d+) is to be created$",
      DataHolder::createSlotLifeCycleManagerBuilder);
    Given("^the slot duration is (\\d+) minutes$", (Integer slotDuration) ->
      createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.slotDurationInMinutes = slotDuration);
    Given("^the slots are open$", (DataTable openDays) ->
      createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.availableDays = openDays.asList(DayOfWeek.class));
    Given("^the slots are open from (.*) to (.*)$", (String startOpenTime, String endOpenTime) ->
      createSlotLifeCycleManagerBuilder.slotsTimetableBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime, clock.getZone(), clock)));
    Given("^the slot lifecycle is created with that configuration$", () ->
      slotLifeCycleManager = createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(createSlotLifeCycleManagerBuilder.build()));
    Given("^a slot lifecycle manager for room (\\d+) is created$", (Long roomId) ->
      slotLifeCycleManager = createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(CreateSlotLifeCycleManagerCommand.of(roomId, SLOT_VALIDATION_INFO)));
    Then("^a slot lifecycle manager for room (.*) should be created$", (Long roomId) ->
      assertThat(slotLifeCycleManagerComponent.find(roomId)).isNotNull());
  }
}
