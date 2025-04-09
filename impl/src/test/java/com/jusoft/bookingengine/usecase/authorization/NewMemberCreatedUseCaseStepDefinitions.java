package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NewMemberCreatedUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private NewMemberCreatedUseCase newMemberCreatedUseCase;

  @When("^user (.*) of club (.*) with member ID (.*) is added to the list of members to manage its authorization$")
  public void user_of_club_with_member_ID_is_added_to_the_list_of_members_to_manage_its_authorization (Long userId, Long clubId, Long memberId) {
    newMemberCreatedUseCase.createMember(memberId, userId, clubId);
  }
  @Then("^the member (\\d+) should be added to the list of members to manage its authorization$")
  public void the_member_should_be_added_to_the_list_of_members_to_manage_its_authorization(Long memberId) {
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getId()).isEqualTo(memberId);
      assertThat(member.get().getBuildings()).isEmpty();
  }
}
