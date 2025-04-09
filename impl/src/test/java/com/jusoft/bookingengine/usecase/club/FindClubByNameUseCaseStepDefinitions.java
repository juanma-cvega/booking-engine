package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class FindClubByNameUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClubManagerComponent clubManagerComponent;

  @Autowired
  private FindClubByNameUseCase findClubByNameUseCase;

  private ClubView clubFound;

  @When("^a user searches for the club (.*)$")
  public void a_user_searches_for_the_club (String clubName) {
    clubFound = findClubByNameUseCase.findByName(clubName);
  }
  @Then("^the user should find the club$")
  public void the_user_should_find_the_club () {
    assertThat(clubFound.getId()).isEqualTo(clubCreated.getId());
    assertThat(clubFound.getDescription()).isEqualTo(clubCreated.getDescription());
  }
}
