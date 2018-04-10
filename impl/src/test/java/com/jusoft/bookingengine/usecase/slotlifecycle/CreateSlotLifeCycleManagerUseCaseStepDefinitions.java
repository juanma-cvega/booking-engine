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
import static org.assertj.core.api.Assertions.assertThat;

public class CreateSlotLifeCycleManagerUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private CreateSlotLifeCycleManagerUseCase createSlotLifeCycleManagerUseCase;

  public CreateSlotLifeCycleManagerUseCaseStepDefinitions() {
    Given("^a slot lifecycle manager (\\d+) is to be created$",
      DataHolder::createSlotLifeCycleManagerBuilder);
    Given("^the slot duration is (\\d+) minutes$", (Integer slotDuration) ->
      createSlotLifeCycleManagerBuilder.slotValidationInfoBuilder.slotDurationInMinutes = slotDuration);
    Given("^the slots are open$", (DataTable openDays) ->
      createSlotLifeCycleManagerBuilder.slotValidationInfoBuilder.availableDays = openDays.asList(DayOfWeek.class));
    Given("^the slots are open from (.*) to (.*)$", (String startOpenTime, String endOpenTime) ->
      createSlotLifeCycleManagerBuilder.slotValidationInfoBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime)));
    Given("^the slot lifecycle is created with that configuration$", () ->
      createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(createSlotLifeCycleManagerBuilder.build()));
    When("^a slot lifecycle manager (\\d+) is created$", (Long roomId) ->
      createSlotLifeCycleManagerUseCase.createSlotLifeCycleManager(CreateSlotLifeCycleManagerCommand.of(roomId, SLOT_VALIDATION_INFO)));
    Then("^a slot lifecycle manager (.*) should be created$", (Long roomId) ->
      assertThat(slotLifeCycleManagerComponent.find(roomId)).isNotNull());
  }
}
