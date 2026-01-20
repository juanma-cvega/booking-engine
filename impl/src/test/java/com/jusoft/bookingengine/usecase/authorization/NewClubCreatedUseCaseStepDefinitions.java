package com.jusoft.bookingengine.usecase.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class NewClubCreatedUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private AuthorizationManagerComponent authorizationManagerComponent;

    @Autowired private NewClubCreatedUseCase newClubCreatedUseCase;

    @When("^club (.*) is added to the list of clubs to manage its authorization$")
    public void club_is_added_to_the_list_of_clubs_to_manage_its_authorization(Long clubId) {
        newClubCreatedUseCase.createClub(clubId);
    }

    @Then("^the club (.*) should be added to the list of clubs to manage its authorization$")
    public void the_club_should_be_added_to_the_list_of_clubs_to_manage_its_authorization(
            Long clubId) {
        Optional<ClubView> clubFound = authorizationManagerComponent.findClubBy(clubId);
        assertThat(clubFound).isPresent();
        assertThat(clubFound.get().getId()).isEqualTo(clubId);
        assertThat(clubFound.get().getBuildings()).isEmpty();
    }
}
