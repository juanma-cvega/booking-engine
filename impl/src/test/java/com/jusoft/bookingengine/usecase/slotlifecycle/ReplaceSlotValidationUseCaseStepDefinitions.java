package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInvalidException;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

import static com.jusoft.bookingengine.holder.DataHolder.slotValidationInfoBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplaceSlotValidationUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private ReplaceSlotValidationUseCase replaceSlotValidationUseCase;

  public ReplaceSlotValidationUseCaseStepDefinitions() {
    Given("^the new slot duration is (\\d+) minutes$", (Integer slotDuration) ->
      slotValidationInfoBuilder.slotDurationInMinutes = slotDuration);
    Given("^the new slots are open$", (DataTable openDays) ->
      slotValidationInfoBuilder.availableDays = openDays.asList(DayOfWeek.class));
    Given("^the new slots are open from (.*) to (.*)$", (String startOpenTime, String endOpenTime) ->
      slotValidationInfoBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime)));
    When("^the slot validation is about to be replaced$",
      DataHolder::createSlotValidationInfoBuilder);
    When("^the slot lifecycle (.*) gets replaced its slot validation information with that configuration$", (Long roomId) ->
      slotLifeCycleManagerComponent.replaceSlotValidationWith(roomId, slotValidationInfoBuilder.build()));
    Then("^the slot lifecycle manager (\\d+) should contain the new slot validation information$", (Long roomId) -> {
      SlotValidationInfo slotValidationInfo = slotValidationInfoBuilder.build();
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getSlotValidationInfo().getAvailableDays()).hasSameElementsAs(slotValidationInfo.getAvailableDays());
      assertThat(managerView.getSlotValidationInfo().getSlotDurationInMinutes()).isEqualTo(slotValidationInfo.getSlotDurationInMinutes());
      assertThat(managerView.getSlotValidationInfo().getOpenTimesPerDay()).hasSameElementsAs(slotValidationInfo.getOpenTimesPerDay());
    });
    When("^the slot lifecycle (\\d+) is tried to be replaced its slot validation information with that configuration$", (Long roomId) -> {
      storeException(() -> replaceSlotValidationUseCase.replaceSlotValidation(roomId, slotValidationInfoBuilder.build()));
    });
    Then("^the admin should be notified the new slot validation information for room (.*) cannot be used$", (Long roomId) -> {
      SlotValidationInvalidException exception = verifyAndGetExceptionThrown(SlotValidationInvalidException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      SlotValidationInfo slotValidationInfo = slotValidationInfoBuilder.build();
      assertThat(exception.getSlotValidationInfo().getAvailableDays()).hasSameElementsAs(slotValidationInfo.getAvailableDays());
      assertThat(exception.getSlotValidationInfo().getSlotDurationInMinutes()).isEqualTo(slotValidationInfo.getSlotDurationInMinutes());
      assertThat(exception.getSlotValidationInfo().getOpenTimesPerDay()).hasSameElementsAs(slotValidationInfo.getOpenTimesPerDay());
    });
  }
}
