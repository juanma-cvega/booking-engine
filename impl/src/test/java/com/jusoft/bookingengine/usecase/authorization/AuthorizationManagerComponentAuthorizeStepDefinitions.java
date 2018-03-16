package com.jusoft.bookingengine.usecase.authorization;

import com.google.common.collect.ImmutableList;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.component.authorization.api.UnauthorisedException;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.PendingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.memberCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
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
      storeException(() -> authorizationManagerComponent.authorise(
        AuthorizeCommand.of(userId, roomId, buildingId, clubId, slotCreationDateTime)));
    });
    Then("^the member should be authorised$", () ->
      assertThat(exceptionThrown).isNull());
    Then("^the member should not be authorised$", () -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorisedException.class);
    });
    Given("^the member is authorised to reserve slots for the room$", () -> {
      authorizationManagerComponent.createClub(clubCreated.getId());
      authorizationManagerComponent.createMember(memberCreated.getId(), memberCreated.getUserId(), memberCreated.getClubId());
    });
    Given("^the user is not an authorised member of the club$", () ->
      authorizationManagerComponent.createClub(clubCreated.getId()));
    Given("^the member is not authorised to reserve slots for the room$", () -> {
      authorizationManagerComponent.createClub(clubCreated.getId());
      authorizationManagerComponent.createMember(memberCreated.getId(), memberCreated.getUserId(), memberCreated.getClubId());
      authorizationManagerComponent.addRoomTagsToClub(AddRoomTagsToClubCommand.of(
        clubCreated.getId(),
        buildingCreated.getId(),
        roomCreated.getId(),
        SlotStatus.NORMAL,
        ImmutableList.of(Tag.of(TAG_ONLY_IN_CLUB))
      ));
    });
    Then("^the user (\\d+) should get a notification that he is not authorised to reserve the slot$", (Long userId) -> {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
    });
    Then("^the user (\\d+) should receive a notification he is not authorised to use room (\\d+) in building (\\d+) in club (\\d+)$", (Long userId, Long roomId, Long buildingId, Long clubId) -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorisedException.class);
      UnauthorisedException exception = (UnauthorisedException) exceptionThrown;
      assertThat(exception.getBuildingId()).isEqualTo(buildingId);
      assertThat(exception.getClubId()).isEqualTo(clubId);
      assertThat(exception.getRoomId()).isEqualTo(roomId);
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
    Then("^the user (\\d+) should receive a notification he is not authorised to use the room created$", (Long userId) -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorisedException.class);
      UnauthorisedException exception = (UnauthorisedException) exceptionThrown;
      assertThat(exception.getBuildingId()).isEqualTo(roomCreated.getBuildingId());
      assertThat(exception.getClubId()).isEqualTo(roomCreated.getClubId());
      assertThat(exception.getRoomId()).isEqualTo(roomCreated.getId());
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
  }
}
