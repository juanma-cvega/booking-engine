package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableOverlappingException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservedSlotsOfDay;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class AddClassTimetableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private AddClassTimetableUseCase addClassTimetableUseCase;

  public AddClassTimetableUseCaseStepDefinitions() {
    When("^class (\\d+) is configured in slot lifecycle manager for room (\\d+) to use$", (Long classId, Long roomId, DataTable dataTable) -> {
      List<ReservedSlotsOfDay> reservedSlots = createDaysReservedSlotsFrom(dataTable);
      ClassTimetable classTimetable = ClassTimetable.of(classId, reservedSlots);
      addClassTimetableUseCase.addClassTimetableTo(roomId, classTimetable);
    });
    Then("^slot lifecycle manager for room (\\d+) should contain class (\\d+) to use the room$", (Long roomId, Long classId, DataTable dataTable) -> {
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getClassesConfig().get(classId)).isNotNull();
      ClassTimetable classTimetable = managerView.getClassesConfig().get(classId);
      assertThat(classTimetable.getClassId()).isEqualTo(classId);
      verifyClassTimetable(createDaysReservedSlotsFrom(dataTable), classTimetable.getReservedSlotsOfDays());
    });
    When("^class (\\d+) is tried to be configured in room (\\d+) to use$", (Long classId, Long roomId, DataTable dataTable) -> {
      List<ReservedSlotsOfDay> daysReservedSlots = createDaysReservedSlotsFrom(dataTable);
      ClassTimetable classTimetable = ClassTimetable.of(classId, daysReservedSlots);
      storeException(() -> addClassTimetableUseCase.addClassTimetableTo(roomId, classTimetable));
    });
    Then("^the admin should receive a notification the class information is not valid for class (\\d+) for room (\\d+)$", (Long classId, Long roomId, DataTable dataTable) -> {
      ClassTimetableNotValidException exception = verifyAndGetExceptionThrown(ClassTimetableNotValidException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getClassTimetable().getClassId()).isEqualTo(classId);
      verifyClassTimetable(createDaysReservedSlotsFrom(dataTable), exception.getClassTimetable().getReservedSlotsOfDays());
    });
    Then("^the admin should receive a notification the class information overlaps with an already reserved slot for class (\\d+) for room (\\d+)$", (Long classId, Long roomId, DataTable dataTable) -> {
      ClassTimetableOverlappingException exception = verifyAndGetExceptionThrown(ClassTimetableOverlappingException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getClassTimetable().getClassId()).isEqualTo(classId);
      verifyClassTimetable(createDaysReservedSlotsFrom(dataTable), exception.getClassTimetable().getReservedSlotsOfDays());
    });
  }

  private List<ReservedSlotsOfDay> createDaysReservedSlotsFrom(DataTable dataTable) {
    return dataTable.transpose().asList(DataHolder.ReservedSlotsOfDayHolder.class).stream()
      .map(reservedSlotsOfDayHolder -> reservedSlotsOfDayHolder.build(clock))
      .collect(toList());
  }

  private void verifyClassTimetable(List<ReservedSlotsOfDay> expectedDaysReservedSlot, List<ReservedSlotsOfDay> foundDaysReservedSlot) {
    assertThat(foundDaysReservedSlot).extracting("dayOfWeek")
      .hasSameElementsAs(expectedDaysReservedSlot.stream().map(ReservedSlotsOfDay::getDayOfWeek).collect(toList()));
    assertThat(foundDaysReservedSlot).extracting("slotsStartTime")
      .hasSameElementsAs(expectedDaysReservedSlot.stream().map(ReservedSlotsOfDay::getSlotsStartTime).collect(toList()));
  }
}
