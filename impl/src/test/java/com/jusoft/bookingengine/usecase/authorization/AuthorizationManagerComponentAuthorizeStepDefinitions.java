package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedReservationException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationManagerComponentAuthorizeStepDefinitions extends AbstractUseCaseStepDefinitions {

  private static final String TAG_ONLY_IN_CLUB = "TAG_ONLY_IN_CLUB";

  @Autowired
  private Clock clock;
  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  public AuthorizationManagerComponentAuthorizeStepDefinitions() {
    When("^admin verifies credentials of user (.*) to access slot created the (.*) at (.*) in room (.*) in building (.*) in club (.*)$", (Long userId, String slotCreationDate, String slotCreationTime, Long roomId, Long buildingId, Long clubId) -> {
      ZonedDateTime slotCreationDateTime = getDateFrom(slotCreationTime, slotCreationDate);
      storeException(() -> authorizationManagerComponent.authorizeReserveSlot(
        AuthorizeCommand.of(userId, roomId, buildingId, clubId, slotCreationDateTime)));
    });
    Then("^the member should be authorized$", () ->
      assertThat(exceptionThrown).isNull());
    Then("^the member should not be authorized$", () -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
    });
    Given("^the club created is managed by the authorization manager$", () ->
      authorizationManagerComponent.createClub(clubCreated.getId()));
    Then("^the user (\\d+) should receive a notification he is not authorized to use room (\\d+) in building (\\d+) in club (\\d+)$", (Long userId, Long roomId, Long buildingId, Long clubId) -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
      UnauthorizedReservationException exception = (UnauthorizedReservationException) exceptionThrown;
      assertThat(exception.getBuildingId()).isEqualTo(buildingId);
      assertThat(exception.getClubId()).isEqualTo(clubId);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
  }
}
