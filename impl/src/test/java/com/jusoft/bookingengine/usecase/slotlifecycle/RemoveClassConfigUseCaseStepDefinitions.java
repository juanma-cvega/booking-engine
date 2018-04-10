package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfigNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoveClassConfigUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private RemoveClassConfigUseCase removeClassConfigUseCase;

  public RemoveClassConfigUseCaseStepDefinitions() {
    When("^the class (\\d+) is removed from room (\\d+)$", (Long classId, Long roomId) -> {
      removeClassConfigUseCase.removeClassConfigFrom(roomId, classId);
    });
    Then("^a slot lifecycle manager (\\d+) should not contain a configuration for class (\\d+)$", (Long roomId, Long classId) -> {
      SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
      assertThat(managerView.getClassesConfig().get(classId)).isNull();
    });
    When("^the class (\\d+) is tried to be removed from room (\\d+)$", (Long classId, Long roomId) -> {
      storeException(() -> removeClassConfigUseCase.removeClassConfigFrom(roomId, classId));
    });
    Then("^the admin should receive a notification the class (\\d+) for slot lifecycle manager (\\d+) does not exist$", (Long classId, Long roomId) -> {
      ClassConfigNotFoundException exception = verifyAndGetExceptionThrown(ClassConfigNotFoundException.class);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getClassId()).isEqualTo(classId);
    });
  }
}
