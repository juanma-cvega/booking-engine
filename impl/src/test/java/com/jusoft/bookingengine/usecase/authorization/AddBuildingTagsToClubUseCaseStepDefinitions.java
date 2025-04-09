package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ClubBuildingView;
import com.jusoft.bookingengine.component.authorization.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

  @When("^club (.*) is added tags? to building (\\d+)$")
  public void club_is_added_tags_to_building (Long clubId, Long buildingId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    addBuildingTagsToClubUseCase.addBuildingTagsToClub(AddBuildingTagsToClubCommand.of(clubId, buildingId, tags));
  }
  @Then("^club (\\d+) should have building (.*) added to its list of buildings$")
  public void club_should_have_building_added_to_its_list_of_buildings (Long clubId, Long buildingId) {
    Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
    assertThat(club).isPresent();
    assertThat(club.get().getBuildings()).isNotEmpty();
    assertThat(club.get().getBuildings().get(buildingId)).isEqualTo(ClubBuildingView.of(buildingId));
  }
  @Then("^building (\\d+) of club (\\d+) should have tags? in its list of tags$")
  public void building_of_club_should_have_tags_in_its_list_of_tags (Long buildingId, Long clubId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
    assertThat(club).isPresent();
    assertThat(club.get().getBuildings().get(buildingId).getTags()).containsExactlyElementsOf(tags);
  }
  @When("^club (.*) is tried to be added tag to building (\\d+)$")
  public void club_is_tried_to_be_added_tag_to_building(Long clubId, Long buildingId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    storeException(() -> addBuildingTagsToClubUseCase.addBuildingTagsToClub(AddBuildingTagsToClubCommand.of(clubId, buildingId, tags)));
  }
  @Then("^the admin should get a notification the club (.*) does not exist$")
  public void the_admin_should_get_a_notification_the_club_does_not_exist(Long clubId) {
    assertThat(exceptionThrown).isInstanceOf(ClubNotFoundException.class);
    ClubNotFoundException exception = (ClubNotFoundException) exceptionThrown;
    assertThat(exception.getClubId()).isEqualTo(clubId);
  }
}
