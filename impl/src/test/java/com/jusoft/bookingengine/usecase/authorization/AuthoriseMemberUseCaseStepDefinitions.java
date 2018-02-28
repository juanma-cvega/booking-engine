package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.CheckAuthorizationCommand;
import com.jusoft.bookingengine.component.authorization.api.Coordinates;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.authorizationGranted;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthoriseMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthoriseMemberUseCase authoriseMemberUseCase;

  public AuthoriseMemberUseCaseStepDefinitions() {
    When("^admin verifies credentials of member (\\d+) to access room (\\d+) with slot status (.*) of building (\\d+) in club (\\d+)$", (Long memberId, Long roomId, SlotStatus status, Long buildingId, Long clubId) -> {
      authorizationGranted = authoriseMemberUseCase.authoriseMember(
        CheckAuthorizationCommand.of(Coordinates.of(buildingId, roomId, status), clubId, memberId));
    });
    Then("^the member should be authorised$", () ->
      assertThat(authorizationGranted).isTrue());
    Then("^the member should not be authorised$", () ->
      assertThat(authorizationGranted).isFalse());
  }
}
