package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetableInvalidException;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jusoft.bookingengine.holder.DataHolder.slotsTimetableBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplaceSlotsTimetableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private ReplaceSlotsTimetableUseCase replaceSlotsTimetableUseCase;

  @Given("^the new slot duration is (\\d+) minutes$")
  public void the_new_slot_duration_is_minutes(Integer slotDuration) {
    slotsTimetableBuilder.slotDurationInMinutes = slotDuration;
  }
  @Given("^the new slots are open$")
  public void the_new_slots_are_open(DataTable openDaysDataTable) {
    List<DayOfWeek> openDays = openDaysDataTable.row(0).stream()
      .map(DayOfWeek::valueOf)
      .collect(Collectors.toList());
    slotsTimetableBuilder.availableDays = openDays;
  }
  @Given("^the new slots are open from (.*) to (.*)$")
  public void the_new_slots_are_open_from_to(String startOpenTime, String endOpenTime) {
    slotsTimetableBuilder.openTimes.add(OpenTime.of(startOpenTime, endOpenTime, clock.getZone(), clock));
  }
  @When("^the slots timetable is about to be replaced$")
  public void the_slots_timetable_is_about_to_be_replaced() {
    DataHolder.createSlotsTimetableBuilder();
  }
  @When("^the slot lifecycle (.*) gets replaced its slots timetable with that configuration$")
  public void the_slot_lifecycle_gets_replaced_its_slots_timetable_with_that_configuration(Long roomId) {
    slotLifeCycleManagerComponent.replaceSlotsTimetableWith(roomId, slotsTimetableBuilder.build());
  }
  @Then("^the slot lifecycle manager for room (\\d+) should contain the new slots timetable$")
  public void the_slot_lifecycle_manager_for_room_should_contain_the_new_slots_timetable(Long roomId) {
    SlotsTimetable slotsTimetable = slotsTimetableBuilder.build();
    SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
    assertThat(managerView.getSlotsTimetable().getAvailableDays()).hasSameElementsAs(slotsTimetable.getAvailableDays());
    assertThat(managerView.getSlotsTimetable().getSlotDurationInMinutes()).isEqualTo(slotsTimetable.getSlotDurationInMinutes());
    assertThat(managerView.getSlotsTimetable().getOpenTimesPerDay()).hasSameElementsAs(slotsTimetable.getOpenTimesPerDay());
  }
  @When("^the slot lifecycle (\\d+) is tried to be replaced its slots timetable with that configuration$")
  public void the_slot_lifecycle_is_tried_to_be_replaced_its_slots_timetable_with_that_configuration (Long roomId) {
    storeException(() -> replaceSlotsTimetableUseCase.replaceSlotsTimetable(roomId, slotsTimetableBuilder.build()));
  }
  @Then("^the admin should be notified the new slots timetable for room (.*) cannot be used$")
  public void the_admin_should_be_notified_the_new_slots_timetable_for_room_cannot_be_used(Long roomId) {
    SlotsTimetableInvalidException exception = verifyAndGetExceptionThrown(SlotsTimetableInvalidException.class);
    assertThat(exception.getRoomId()).isEqualTo(roomId);
    SlotsTimetable slotsTimetable = slotsTimetableBuilder.build();
    assertThat(exception.getSlotsTimetable().getAvailableDays()).hasSameElementsAs(slotsTimetable.getAvailableDays());
    assertThat(exception.getSlotsTimetable().getSlotDurationInMinutes()).isEqualTo(slotsTimetable.getSlotDurationInMinutes());
    assertThat(exception.getSlotsTimetable().getOpenTimesPerDay()).hasSameElementsAs(slotsTimetable.getOpenTimesPerDay());
  }
}
