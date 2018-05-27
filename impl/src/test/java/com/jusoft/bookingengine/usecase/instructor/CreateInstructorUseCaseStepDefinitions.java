package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorCreatedEvent;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.jusoft.bookingengine.fixture.InstructorFixtures.INSTRUCTOR_PERSONAL_INFO;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.instructorCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateInstructorUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private InstructorManagerComponent instructorManagerComponent;

  @Autowired
  private CreateInstructorUseCase createInstructorUseCase;

  public CreateInstructorUseCaseStepDefinitions() {
    When("^an instructor is created$", () ->
      instructorCreated = createInstructorUseCase.createInstructor(CreateInstructorCommand.of(clubCreated.getId(), INSTRUCTOR_PERSONAL_INFO)));
    Then("^the instructor should be registered in the club$", () -> {
      Optional<InstructorView> instructor = instructorManagerComponent.find(instructorCreated.getId());
      assertThat(instructor).isPresent();
      InstructorView instructorFound = instructor.get();
      assertThat(instructorFound.getBuildings()).isEqualTo(instructorCreated.getBuildings()).isEmpty();
      assertThat(instructorFound.getClubId()).isEqualTo(instructorCreated.getClubId()).isEqualTo(clubCreated.getId());
      assertThat(instructorFound.getPersonalInfo()).isEqualTo(instructorCreated.getPersonalInfo()).isEqualTo(INSTRUCTOR_PERSONAL_INFO);
      assertThat(instructorFound.getSupportedClassTypes()).isEqualTo(instructorCreated.getSupportedClassTypes()).isEmpty();
    });
    Then("^a notification of a registered instructor should be published$", () -> {
      InstructorCreatedEvent event = verifyAndGetMessageOfType(InstructorCreatedEvent.class);
      assertThat(event.getInstructorId()).isEqualTo(instructorCreated.getId());
      assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
    });
  }
}
