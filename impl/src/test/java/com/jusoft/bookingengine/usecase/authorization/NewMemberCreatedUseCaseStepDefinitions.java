package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NewMemberCreatedUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private NewMemberCreatedUseCase newMemberCreatedUseCase;

  public NewMemberCreatedUseCaseStepDefinitions() {
    When("^member (\\d+) is added to the list of members to manage its authorization$", (Long memberId) ->
      newMemberCreatedUseCase.createMember(memberId)
    );
    Then("^the member (\\d+) should be added to the list of members to manage its authorization$", (Long memberId) -> {
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getId()).isEqualTo(memberId);
      assertThat(member.get().getBuildings()).isEmpty();
    });
  }
}
