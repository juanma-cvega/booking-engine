package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.building.api.BuildingCreatedEvent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.fixture.BuildingFixtures.ADDRESS;
import static com.jusoft.bookingengine.fixture.BuildingFixtures.BUILDING_DESCRIPTION;
import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateBuildingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private BuildingComponent buildingComponent;

  @Autowired
  private CreateBuildingUseCase createBuildingUseCase;

  public CreateBuildingUseCaseStepDefinitions() {
    When("^a building is created$", () ->
      buildingCreated = createBuildingUseCase.createBuildingFrom(
        new CreateBuildingCommand(clubCreated.getId(), ADDRESS, BUILDING_DESCRIPTION)));
    Then("^the building should be stored$", () -> {
      BuildingView buildingView = buildingComponent.find(buildingCreated.getId());
      assertThat(buildingView.getAddress()).isEqualTo(buildingCreated.getAddress());
      assertThat(buildingView.getClubId()).isEqualTo(buildingCreated.getClubId());
      assertThat(buildingView.getDescription()).isEqualTo(buildingCreated.getDescription());
    });
    Then("^a notification of a created building should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(BuildingCreatedEvent.class);
      BuildingCreatedEvent buildingCreatedEvent = (BuildingCreatedEvent) messageCaptor.getValue();
      assertThat(buildingCreatedEvent.getBuildingId()).isEqualTo(buildingCreated.getId());
      assertThat(buildingCreatedEvent.getAddress()).isEqualTo(buildingCreated.getAddress());
      assertThat(buildingCreatedEvent.getDescription()).isEqualTo(buildingCreated.getDescription());
    });
  }
}
