package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassInstructorRemovedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassListOfInstructorsCannotBeEmptyException;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.RemoveInstructorCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class RemoveInstructorUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private RemoveInstructorUseCase removeInstructorUseCase;

  @When("^instructor (\\d+) is removed from the class$")
  public void instructor_is_removed_from_the_class (Long instructorId) {
    removeInstructorUseCase.removeInstructor(RemoveInstructorCommand.of(classCreated.getId(), instructorId));
  }
  @Then("^the class should not have instructor (\\d+) as an available instructor$")
  public void the_class_should_not_have_instructor_as_an_available_instructor(Long instructorId) {
    ClassView classFound = classManagerComponent.find(classCreated.getId());
    assertThat(classFound.getInstructorsId()).doesNotContain(instructorId);
  }
  @When("^instructor (\\d+) is tried to be removed from the class$")
  public void instructor_is_tried_to_be_removed_from_the_class(Long instructorId) {
    storeException(() -> removeInstructorUseCase.removeInstructor(RemoveInstructorCommand.of(classCreated.getId(), instructorId)));
  }
  @Then("^a notification of the removed instructor (\\d+) from the class should published$")
  public void a_notification_of_the_removed_instructor_from_the_class_should_published (Long instructorId) {
    ClassInstructorRemovedEvent event = verifyAndGetMessageOfType(ClassInstructorRemovedEvent.class);
    assertThat(event.getClassId()).isEqualTo(classCreated.getId());
    assertThat(event.getInstructorId()).isEqualTo(instructorId);
  };
  @Then("^the admin should be notified the instructor (\\d+) cannot be removed and leave the list of instructors empty for the class$")
  public void the_admin_should_be_notified_the_instructor_cannot_be_removed_and_leave_the_list_of_instructors_empty_for_the_class (Long instructorId) {
    ClassListOfInstructorsCannotBeEmptyException exception = verifyAndGetExceptionThrown(ClassListOfInstructorsCannotBeEmptyException.class);
    assertThat(exception.getClassId()).isEqualTo(classCreated.getId());
    assertThat(exception.getInstructorId()).isEqualTo(instructorId);
  };
}
