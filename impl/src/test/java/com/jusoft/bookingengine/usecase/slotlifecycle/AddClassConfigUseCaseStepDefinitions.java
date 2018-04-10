package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfig;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigOverlappingException;
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

public class AddClassConfigUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private AddClassConfigUseCase addClassConfigUseCase;

  public AddClassConfigUseCaseStepDefinitions() {
    When("^class (\\d+) is configured in room (\\d+) to use$", (Long classId, Long roomId, DataTable dataTable) -> {
      List<ReservedSlotsOfDay> daysReservedSlots = createDaysReservedSlotsFrom(dataTable);
      ClassConfig classConfig = ClassConfig.of(classId, daysReservedSlots);
      addClassConfigUseCase.addClassConfigTo(roomId, classConfig);
    });
    Then("^slot lifecycle manager (\\d+) should contain class (\\d+) to use the room$", (Long roomId, Long classId, DataTable dataTable) -> {
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getClassesConfig().get(classId)).isNotNull();
      ClassConfig classConfig = managerView.getClassesConfig().get(classId);
      assertThat(classConfig.getClassId()).isEqualTo(classId);
      verifyClassConfig(createDaysReservedSlotsFrom(dataTable), classConfig.getReservedSlotsOfDays());
    });
    When("^class (\\d+) is tried to be configured in room (\\d+) to use$", (Long classId, Long roomId, DataTable dataTable) -> {
      List<ReservedSlotsOfDay> daysReservedSlots = createDaysReservedSlotsFrom(dataTable);
      ClassConfig classConfig = ClassConfig.of(classId, daysReservedSlots);
      storeException(() -> addClassConfigUseCase.addClassConfigTo(roomId, classConfig));
    });
    Then("^the admin should receive a notification the class information is not valid for class (\\d+) for room (\\d+)$", (Long classId, Long roomId, DataTable dataTable) -> {
      ClassConfigNotValidException exception = verifyAndGetExceptionThrown(ClassConfigNotValidException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getClassConfig().getClassId()).isEqualTo(classId);
      verifyClassConfig(createDaysReservedSlotsFrom(dataTable), exception.getClassConfig().getReservedSlotsOfDays());
    });
    Then("^the admin should receive a notification the class information overlaps with an already reserved slot for class (\\d+) for room (\\d+)$", (Long classId, Long roomId, DataTable dataTable) -> {
      ClassConfigOverlappingException exception = verifyAndGetExceptionThrown(ClassConfigOverlappingException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getClassConfig().getClassId()).isEqualTo(classId);
      verifyClassConfig(createDaysReservedSlotsFrom(dataTable), exception.getClassConfig().getReservedSlotsOfDays());
    });
  }

  private List<ReservedSlotsOfDay> createDaysReservedSlotsFrom(DataTable dataTable) {
    return dataTable.transpose().asList(DataHolder.DayReservedSlotsHolder.class).stream()
      .map(dayReservedSlotsHolder -> dayReservedSlotsHolder.build(clock))
      .collect(toList());
  }

  private void verifyClassConfig(List<ReservedSlotsOfDay> expectedDaysReservedSlot, List<ReservedSlotsOfDay> foundDaysReservedSlot) {
    assertThat(foundDaysReservedSlot).extracting("dayOfWeek")
      .hasSameElementsAs(expectedDaysReservedSlot.stream().map(ReservedSlotsOfDay::getDayOfWeek).collect(toList()));
    assertThat(foundDaysReservedSlot).extracting("zoneId")
      .hasSameElementsAs(expectedDaysReservedSlot.stream().map(ReservedSlotsOfDay::getZoneId).collect(toList()));
    assertThat(foundDaysReservedSlot).extracting("slotsStartTime")
      .hasSameElementsAs(expectedDaysReservedSlot.stream().map(ReservedSlotsOfDay::getSlotsStartTime).collect(toList()));
  }
}
