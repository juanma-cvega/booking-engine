package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberBuildingView;
import com.jusoft.bookingengine.component.authorization.api.MemberNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
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

public class AddBuildingTagsToMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddBuildingTagsToMemberUseCase addBuildingTagsToMemberUseCase;

  @When("^member (.*) is added tags? to building (\\d+)$")
  public void member_is_added_tags_to_building (Long memberId, Long buildingId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    addBuildingTagsToMemberUseCase.addBuildingTagsToMember(AddBuildingTagsToMemberCommand.of(memberId, buildingId, tags));
  }
  @Then("^member (.*) should have building (\\d+) added to its list of buildings$")
  public void member_should_have_building_added_to_its_list_of_buildings (Long memberId, Long buildingId) {
    Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
    assertThat(member).isPresent();
    assertThat(member.get().getBuildings()).isNotEmpty();
    assertThat(member.get().getBuildings().get(buildingId)).isEqualTo(MemberBuildingView.of(buildingId));
  }
  @Then("^building (\\d+) of member (\\d+) should have tags? in its list of tags$")
  public void building_of_member_should_have_tags_in_its_list_of_tags (Long buildingId, Long memberId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
    assertThat(member).isPresent();
    assertThat(member.get().getBuildings().get(buildingId).getTags()).containsExactlyElementsOf(tags);
  }
  @When("^member (.*) is tried to be added tag to building (\\d+)$")
  public void member_is_tried_to_be_added_tag_to_building(Long memberId, Long buildingId, DataTable tagsDataTable) {
    List<Tag> tags = tagsDataTable.asList().stream().map(Tag::of).collect(Collectors.toList());
    storeException(() -> addBuildingTagsToMemberUseCase.addBuildingTagsToMember(AddBuildingTagsToMemberCommand.of(memberId, buildingId, tags)));
  }
  @Then("^the admin should get a notification the member (.*) does not exist$")
  public void the_admin_should_get_a_notification_the_member_does_not_exist$(Long memberId) {
    assertThat(exceptionThrown).isInstanceOf(MemberNotFoundException.class);
    MemberNotFoundException exception = (MemberNotFoundException) exceptionThrown;
    assertThat(exception.getMemberId()).isEqualTo(memberId);
  }
}
