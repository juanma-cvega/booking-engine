package com.jusoft.bookingengine.usecase.classmanager;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.classmanager.api.AddInstructorCommand;
import com.jusoft.bookingengine.component.classmanager.api.ClassInstructorAddedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class AddInstructorUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private ClassManagerComponent classManagerComponent;

    @Autowired private AddInstructorUseCase addInstructorUseCase;

    @When("^instructor (\\d+) is added to the class$")
    public void instructor_is_added_to_the_class(Long instructorId) {
        addInstructorUseCase.addInstructor(
                AddInstructorCommand.of(classCreated.getId(), instructorId));
    }

    @Then("^the class should have instructor (\\d+) as an available instructor$")
    public void the_class_should_have_instructor_as_an_available_instructor(Long instructorId) {
        ClassView classFound = classManagerComponent.find(classCreated.getId());
        assertThat(classFound.getInstructorsId()).contains(instructorId);
    }

    @Then("^a notification of the newly added instructor (\\d+) to the class should published$")
    public void a_notification_of_the_newly_added_instructor_to_the_class_should_published(
            Long instructorId) {
        ClassInstructorAddedEvent event =
                verifyAndGetMessageOfType(ClassInstructorAddedEvent.class);
        assertThat(event.classId()).isEqualTo(classCreated.getId());
        assertThat(event.newInstructorId()).isEqualTo(instructorId);
    }
    ;
}
