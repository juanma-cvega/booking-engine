package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassNotFoundException;
import com.jusoft.bookingengine.component.classmanager.api.ClassRemovedEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class RemoveClassUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private RemoveClassUseCase removeClassUseCase;

  public RemoveClassUseCaseStepDefinitions() {
    When("^the class is remove$", () ->
      removeClassUseCase.removeClass(classCreated.getId()));
    Then("^a class should be removed$", () -> {
      ClassNotFoundException exception = catchThrowableOfType(() ->
        classManagerComponent.find(classCreated.getId()), ClassNotFoundException.class);
      assertThat(exception.getClassId()).isEqualTo(classCreated.getId());
    });
    Then("^a notification of a removed class should published$", () -> {
      ClassRemovedEvent event = verifyAndGetMessageOfType(ClassRemovedEvent.class);
      assertThat(event.getClassId()).isEqualTo(classCreated.getId());
    });
  }
}
