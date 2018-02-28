package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NewClubCreatedUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private NewClubCreatedUseCase newClubCreatedUseCase;

  public NewClubCreatedUseCaseStepDefinitions() {
    When("^club (.*) is added to the list of clubs to manage its authorization$", (Long clubId) ->
      newClubCreatedUseCase.createClub(clubId));
    Then("^the club (.*) should be added to the list of clubs to manage its authorization$", (Long clubId) -> {
      Optional<ClubView> clubFound = authorizationManagerComponent.findClubBy(clubId);
      assertThat(clubFound).isPresent();
      assertThat(clubFound.get().getId()).isEqualTo(clubId);
      assertThat(clubFound.get().getBuildings()).isEmpty();
    });
  }
}
