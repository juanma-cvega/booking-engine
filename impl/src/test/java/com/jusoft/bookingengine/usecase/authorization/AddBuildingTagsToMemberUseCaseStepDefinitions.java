package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.BuildingView;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AddBuildingTagsToMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddBuildingTagsToMemberUseCase addBuildingTagsToMemberUseCase;

  public AddBuildingTagsToMemberUseCaseStepDefinitions() {
    When("^member (\\d+) is added tags? to building (\\d+)$", (Long memberId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      addBuildingTagsToMemberUseCase.addBuildingTagsToMember(AddBuildingTagsToMemberCommand.of(memberId, buildingId, tags));
    });
    Then("^member (\\d+) should have building (\\d+) added to its list of buildings$", (Long memberId, Long buildingId) -> {
      Optional<MemberView> club = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings()).isNotEmpty();
      assertThat(club.get().getBuildings().get(buildingId)).isEqualTo(BuildingView.of(buildingId));
    });
    Then("^building (\\d+) of member (\\d+) should have tags? in its list of tags$", (Long memberId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      Optional<MemberView> club = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings().get(buildingId).getTags()).containsExactlyElementsOf(tags);
    });
  }
}
