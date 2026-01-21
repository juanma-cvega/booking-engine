package com.jusoft.bookingengine.usecase.classmanager;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class FindClassUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private ClassManagerComponent classManagerComponent;

    @Autowired private FindClassUseCase findClassUseCase;

    private ClassView classFound;

    @When("^the class is looked up$")
    public void the_class_is_looked_up() {
        classFound = findClassUseCase.findClass(classCreated.id());
    }

    @Then("^a class should be found$")
    public void a_class_should_be_found() {
        assertThat(classFound).isNotNull();
        assertThat(classFound.buildingId()).isEqualTo(classCreated.buildingId());
        assertThat(classFound.description()).isEqualTo(classCreated.description());
        assertThat(classFound.classType()).isEqualTo(classCreated.classType());
        assertThat(classFound.roomsRegistered()).isEmpty();
        assertThat(classFound.instructorsId()).hasSameElementsAs(classCreated.instructorsId());
    }
}
