package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class FindClubByNameUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClubComponent clubComponent;

  @Autowired
  private FindClubByNameUseCase findClubByNameUseCase;

  private ClubView clubFound;

  public FindClubByNameUseCaseStepDefinitions() {
    When("^a user searches for the club (.*)$", (String clubName) ->
      clubFound = findClubByNameUseCase.findByName(clubName));
    Then("^the user should find the club$", () -> {
      assertThat(clubFound.getId()).isEqualTo(clubCreated.getId());
      assertThat(clubFound.getDescription()).isEqualTo(clubCreated.getDescription());
    });
  }
}
