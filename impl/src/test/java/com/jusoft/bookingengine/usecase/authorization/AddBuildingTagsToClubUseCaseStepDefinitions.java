package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.BuildingView;
import com.jusoft.bookingengine.component.authorization.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBuildingTagsToClubUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddBuildingTagsToClubUseCase addBuildingTagsToClubUseCase;

  public AddBuildingTagsToClubUseCaseStepDefinitions() {
    When("^club (\\d+) is added tags? to building (\\d+)$", (Long clubId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      addBuildingTagsToClubUseCase.addBuildingTagsToClub(AddBuildingTagsToClubCommand.of(clubId, buildingId, tags));
    });
    Then("^club (\\d+) should have building (.*) added to its list of buildings$", (Long clubId, Long buildingId) -> {
      Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings()).isNotEmpty();
      assertThat(club.get().getBuildings().get(buildingId)).isEqualTo(BuildingView.of(buildingId));
    });
    Then("^building (\\d+) of club (\\d+) should have tags? in its list of tags$", (Long clubId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings().get(buildingId).getTags()).containsExactlyElementsOf(tags);
    });
    When("^club (\\d+) is tried to be added tag to building (\\d+)$", (Long clubId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      storeException(() -> addBuildingTagsToClubUseCase.addBuildingTagsToClub(AddBuildingTagsToClubCommand.of(clubId, buildingId, tags)));
    });
    Then("^the admin should get a notification the club (.*) does not exist$", (Long clubId) -> {
      assertThat(exceptionThrown).isInstanceOf(ClubNotFoundException.class);
      ClubNotFoundException exception = (ClubNotFoundException) exceptionThrown;
      assertThat(exception.getClubId()).isEqualTo(clubId);
    });
  }
}
