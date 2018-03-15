package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberBuildingView;
import com.jusoft.bookingengine.component.authorization.api.MemberNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBuildingTagsToMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddBuildingTagsToMemberUseCase addBuildingTagsToMemberUseCase;

  public AddBuildingTagsToMemberUseCaseStepDefinitions() {
    When("^member (.*) is added tags? to building (\\d+)$", (Long memberId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      addBuildingTagsToMemberUseCase.addBuildingTagsToMember(AddBuildingTagsToMemberCommand.of(memberId, buildingId, tags));
    });
    Then("^member (.*) should have building (\\d+) added to its list of buildings$", (Long memberId, Long buildingId) -> {
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getBuildings()).isNotEmpty();
      assertThat(member.get().getBuildings().get(buildingId)).isEqualTo(MemberBuildingView.of(buildingId));
    });
    Then("^building (\\d+) of member (\\d+) should have tags? in its list of tags$", (Long buildingId, Long memberId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getBuildings().get(buildingId).getTags()).containsExactlyElementsOf(tags);
    });
    When("^member (.*) is tried to be added tag to building (\\d+)$", (Long memberId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      storeException(() -> addBuildingTagsToMemberUseCase.addBuildingTagsToMember(AddBuildingTagsToMemberCommand.of(memberId, buildingId, tags)));
    });
    Then("^the admin should get a notification the member (.*) does not exist$", (Long memberId) -> {
      assertThat(exceptionThrown).isInstanceOf(MemberNotFoundException.class);
      MemberNotFoundException exception = (MemberNotFoundException) exceptionThrown;
      assertThat(exception.getMemberId()).isEqualTo(memberId);
    });
  }
}
