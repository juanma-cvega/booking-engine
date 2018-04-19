package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetableInvalidException;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

import static com.jusoft.bookingengine.holder.DataHolder.slotsTimetableBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplaceSlotsTimetableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private ReplaceSlotsTimetableUseCase replaceSlotsTimetableUseCase;

  public ReplaceSlotsTimetableUseCaseStepDefinitions() {
    Given("^the new slot duration is (\\d+) minutes$", (Integer slotDuration) ->
      slotsTimetableBuilder.slotDurationInMinutes = slotDuration);
    Given("^the new slots are open$", (DataTable openDays) ->
      slotsTimetableBuilder.availableDays = openDays.asList(DayOfWeek.class));
    Given("^the new slots are open from (.*) to (.*)$", (String startOpenTime, String endOpenTime) ->
      slotsTimetableBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime, clock.getZone(), clock)));
    When("^the slots timetable is about to be replaced$",
      DataHolder::createSlotsTimetableBuilder);
    When("^the slot lifecycle (.*) gets replaced its slots timetable with that configuration$", (Long roomId) ->
      slotLifeCycleManagerComponent.replaceSlotsTimetableWith(roomId, slotsTimetableBuilder.build()));
    Then("^the slot lifecycle manager for room (\\d+) should contain the new slots timetable$", (Long roomId) -> {
      SlotsTimetable slotsTimetable = slotsTimetableBuilder.build();
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getSlotsTimetable().getAvailableDays()).hasSameElementsAs(slotsTimetable.getAvailableDays());
      assertThat(managerView.getSlotsTimetable().getSlotDurationInMinutes()).isEqualTo(slotsTimetable.getSlotDurationInMinutes());
      assertThat(managerView.getSlotsTimetable().getOpenTimesPerDay()).hasSameElementsAs(slotsTimetable.getOpenTimesPerDay());
    });
    When("^the slot lifecycle (\\d+) is tried to be replaced its slots timetable with that configuration$", (Long roomId) -> {
      storeException(() -> replaceSlotsTimetableUseCase.replaceSlotsTimetable(roomId, slotsTimetableBuilder.build()));
    });
    Then("^the admin should be notified the new slots timetable for room (.*) cannot be used$", (Long roomId) -> {
      SlotsTimetableInvalidException exception = verifyAndGetExceptionThrown(SlotsTimetableInvalidException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      SlotsTimetable slotsTimetable = slotsTimetableBuilder.build();
      assertThat(exception.getSlotsTimetable().getAvailableDays()).hasSameElementsAs(slotsTimetable.getAvailableDays());
      assertThat(exception.getSlotsTimetable().getSlotDurationInMinutes()).isEqualTo(slotsTimetable.getSlotDurationInMinutes());
      assertThat(exception.getSlotsTimetable().getOpenTimesPerDay()).hasSameElementsAs(slotsTimetable.getOpenTimesPerDay());
    });
  }
}
