package com.jusoft.bookingengine.usecase.classmanager;

import static com.jusoft.bookingengine.fixture.ClassFixtures.CLASS_DESCRIPTION;
import static com.jusoft.bookingengine.fixture.ClassFixtures.CLASS_TYPE;
import static com.jusoft.bookingengine.fixture.ClassFixtures.CREATE_CLASS_COMMAND_SUPPLIER;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.buildingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static com.jusoft.bookingengine.holder.DataHolder.classesCreated;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.classmanager.api.ClassCreatedEvent;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateClassUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    private static final long INSTRUCTOR_ID = 44L; // FIXME add the id of a created instructor

    @Autowired private ClassManagerComponent classManagerComponent;

    @Autowired private CreateClassUseCase createClassUseCase;

    @Given("^a class is created$")
    public void a_class_is_created() {
        classCreated =
                createClassUseCase.createClass(
                        CREATE_CLASS_COMMAND_SUPPLIER.apply(
                                buildingCreated.getId(), singletonList(INSTRUCTOR_ID)));
        classesCreated.add(classCreated);
    }

    @Given("^a class is created with instructor (\\d+)$")
    public void a_class_is_created_with_instructor(Long instructorId) {
        classCreated =
                createClassUseCase.createClass(
                        CREATE_CLASS_COMMAND_SUPPLIER.apply(
                                buildingCreated.getId(), singletonList(instructorId)));
        classesCreated.add(classCreated);
    }

    @Given("^a class is created for created building (\\d+)$")
    public void a_class_is_created_for_created_building(Integer createdBuildingIndex) {
        BuildingView building = buildingsCreated.get(createdBuildingIndex - 1);
        classCreated =
                createClassUseCase.createClass(
                        CREATE_CLASS_COMMAND_SUPPLIER.apply(
                                building.getId(), singletonList(INSTRUCTOR_ID)));
        classesCreated.add(classCreated);
    }

    @Then("^a class should be created$")
    public void a_class_should_be_created() {
        ClassView classFound = classManagerComponent.find(classCreated.getId());
        assertThat(classFound.getClassType())
                .isEqualTo(CLASS_TYPE)
                .isEqualTo(classCreated.getClassType());
        assertThat(classFound.getDescription())
                .isEqualTo(CLASS_DESCRIPTION)
                .isEqualTo(classCreated.getDescription());
        assertThat(classFound.getInstructorsId())
                .containsExactlyInAnyOrder(INSTRUCTOR_ID)
                .hasSameElementsAs(classCreated.getInstructorsId());
        assertThat(classFound.getRoomsRegistered()).isEmpty();
    }

    @Then("^a notification of a created class should published$")
    public void a_notification_of_a_created_class_should_published() {
        ClassCreatedEvent event = verifyAndGetMessageOfType(ClassCreatedEvent.class);
        assertThat(event.classId()).isEqualTo(classCreated.getId());
        assertThat(event.classType()).isEqualTo(classCreated.getClassType());
        assertThat(event.description()).isEqualTo(classCreated.getDescription());
        assertThat(event.instructorsId()).isEqualTo(classCreated.getInstructorsId());
    }
}
