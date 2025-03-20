package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedReservationException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationManagerComponentAuthorizeStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private Clock clock;
  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @When("^admin verifies credentials of user (.*) to access slot created the (.*) at (.*) in room (.*) in building (.*) in club (.*)$")
  public void admin_verifies_credentials_of_user_to_access_slot_created_in_room_in_building_in_club (Long userId, String slotCreationDate, String slotCreationTime, Long roomId, Long buildingId, Long clubId) {
    ZonedDateTime slotCreationDateTime = getDateFrom(slotCreationTime, slotCreationDate);
    storeException(() -> authorizationManagerComponent.authorizeReserveSlot(
      AuthorizeCommand.of(userId, roomId, buildingId, clubId, slotCreationDateTime)));
  }
  @Then("^the member should be authorized$")
  public void the_member_should_be_authorized() {
    assertThat(exceptionThrown).isNull();
  }
  @Then("^the member should not be authorized$")
  public void the_member_should_not_be_authorize() {
    assertThat(exceptionThrown).isNotNull();
    assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
  }
  @Given("^the club created is managed by the authorization manager$")
  public void the_club_created_is_managed_by_the_authorization_manager() {
    authorizationManagerComponent.createClub(clubCreated.getId());
  }
  @Then("^the user (\\d+) should receive a notification he is not authorized to use room (\\d+) in building (\\d+) in club (\\d+)$")
  public void the_user_should_receive_a_notification_he_is_not_authorized_to_use_room_in_building_in_club (Long userId, Long roomId, Long buildingId, Long clubId) {
    assertThat(exceptionThrown).isNotNull();
    assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
    UnauthorizedReservationException exception = (UnauthorizedReservationException) exceptionThrown;
    assertThat(exception.getBuildingId()).isEqualTo(buildingId);
    assertThat(exception.getClubId()).isEqualTo(clubId);
    assertThat(exception.getRoomId()).isEqualTo(roomId);
    assertThat(exception.getUserId()).isEqualTo(userId);
  }
}
