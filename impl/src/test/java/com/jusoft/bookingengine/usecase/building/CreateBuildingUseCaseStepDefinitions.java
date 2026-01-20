package com.jusoft.bookingengine.usecase.building;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.ADDRESS;
import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_DESCRIPTION;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.buildingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.jusoft.bookingengine.component.building.api.BuildingCreatedEvent;
import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateBuildingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    private static final int NON_EXISTING_CLUB_ID = 567;

    @Autowired private BuildingManagerComponent buildingManagerComponent;

    @Autowired private CreateBuildingUseCase createBuildingUseCase;

    @When("^a building is created$")
    public void a_building_is_created() {
        buildingCreated =
                createBuildingUseCase.createBuildingFrom(
                        CreateBuildingCommand.of(
                                clubCreated.getId(), ADDRESS, BUILDING_DESCRIPTION));
        buildingsCreated.add(buildingCreated);
    }
    ;

    @Then("^the building should be stored$")
    public void the_building_should_be_stored() {
        BuildingView buildingView = buildingManagerComponent.find(buildingCreated.getId());
        assertThat(buildingView.getAddress()).isEqualTo(buildingCreated.getAddress());
        assertThat(buildingView.getClubId()).isEqualTo(buildingCreated.getClubId());
        assertThat(buildingView.getDescription()).isEqualTo(buildingCreated.getDescription());
    }

    @Then("^a notification of a created building should be published$")
    public void a_notification_of_a_created_building_should_be_published() {
        verify(messagePublisher).publish(messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isInstanceOf(BuildingCreatedEvent.class);
        BuildingCreatedEvent buildingCreatedEvent = (BuildingCreatedEvent) messageCaptor.getValue();
        assertThat(buildingCreatedEvent.buildingId()).isEqualTo(buildingCreated.getId());
        assertThat(buildingCreatedEvent.address()).isEqualTo(buildingCreated.getAddress());
        assertThat(buildingCreatedEvent.description()).isEqualTo(buildingCreated.getDescription());
    }
    ;

    @When("^a building is created for a non existing club$")
    public void a_building_is_created_for_a_non_existing_club() {
        storeException(
                () ->
                        createBuildingUseCase.createBuildingFrom(
                                CreateBuildingCommand.of(
                                        NON_EXISTING_CLUB_ID, ADDRESS, BUILDING_DESCRIPTION)));
    }

    @Then("^the user should be notified the club does not exist$")
    public void the_user_should_be_notified_the_club_does_not_exist() {
        assertThat(exceptionThrown).isInstanceOf(ClubNotFoundException.class);
        ClubNotFoundException exception = (ClubNotFoundException) exceptionThrown;
        assertThat(exception.getClubId()).isEqualTo(NON_EXISTING_CLUB_ID);
    }
}
