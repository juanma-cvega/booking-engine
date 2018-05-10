package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassInstructorRemovedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassListOfInstructorsCannotBeEmptyException;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.RemoveInstructorCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class RemoveInstructorUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private RemoveInstructorUseCase removeInstructorUseCase;

  public RemoveInstructorUseCaseStepDefinitions() {
    When("^instructor (\\d+) is removed from the class$", (Long instructorId) ->
      removeInstructorUseCase.removeInstructor(RemoveInstructorCommand.of(classCreated.getId(), instructorId)));
    Then("^the class should not have instructor (\\d+) as an available instructor$", (Long instructorId) -> {
      ClassView classFound = classManagerComponent.find(classCreated.getId());
      assertThat(classFound.getInstructorsId()).doesNotContain(instructorId);
    });
    When("^instructor (\\d+) is tried to be removed from the class$", (Long instructorId) ->
      storeException(() -> removeInstructorUseCase.removeInstructor(RemoveInstructorCommand.of(classCreated.getId(), instructorId))));
    Then("^a notification of the removed instructor (\\d+) from the class should published$", (Long instructorId) -> {
      ClassInstructorRemovedEvent event = verifyAndGetMessageOfType(ClassInstructorRemovedEvent.class);
      assertThat(event.getClassId()).isEqualTo(classCreated.getId());
      assertThat(event.getInstructorId()).isEqualTo(instructorId);
    });
    Then("^the admin should be notified the instructor (\\d+) cannot be removed and leave the list of instructors empty for the class$", (Long instructorId) -> {
      ClassListOfInstructorsCannotBeEmptyException exception = verifyAndGetExceptionThrown(ClassListOfInstructorsCannotBeEmptyException.class);
      assertThat(exception.getClassId()).isEqualTo(classCreated.getId());
      assertThat(exception.getInstructorId()).isEqualTo(instructorId);
    });
  }
}
