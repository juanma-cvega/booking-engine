package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotAuthorizationConfig;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplaceSlotAuthorizationConfigUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;
  @Autowired
  private ReplaceSlotAuthenticationConfigForRoomUseCase useCase;

  @When("^club (\\d+) is tried to be added the slot authorization configuration of (\\d+) (.*) for room (\\d+) in building (\\d+)$")
  public void club_is_tried_to_be_added_the_slot_authorization_configuration_of_for_room_in_building (Long clubId, Long amount, ChronoUnit unit, Long roomId, Long buildingId) {
    storeException(() -> replaceSlotAuthConf(clubId, amount, unit, roomId, buildingId));
  }
  @When("^club (\\d+) is added the slot authorization configuration of (\\d+) (.*) for room (\\d+) in building (\\d+)$")
  public void club_is_added_the_slot_authorization_configuration_of_for_room_in_building (Long clubId, Long amount, ChronoUnit unit, Long roomId, Long buildingId) {
    replaceSlotAuthConf(clubId, amount, unit, roomId, buildingId);
  }
  @Then("^room (\\d+) in building (\\d+) of club (\\d+) should have a slot authorization configuration of (\\d+) (.*)")
  public void room_in_building_of_club_should_have_a_slot_authorization_configuration_of (Long roomId, Long buildingId, Long clubId, Long amount, ChronoUnit unit) {
    Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
    assertThat(club).isPresent();
    SlotAuthorizationConfig config = club.get().getBuildings().get(buildingId).getRooms().get(roomId).getSlotAuthorizationConfig();
    assertThat(config.getAmount()).isEqualTo(amount);
    assertThat(config.getTemporalUnit()).isEqualTo(unit);
  }

  private void replaceSlotAuthConf(Long clubId, Long amount, ChronoUnit unit, Long roomId, Long buildingId) {
    SlotAuthorizationConfig slotAuthorizationConfig = SlotAuthorizationConfig.of(amount, unit);
    useCase.replaceSlotAuthenticationConfigForRoom(ReplaceSlotAuthenticationConfigForRoomCommand.of(
      clubId, buildingId, roomId, slotAuthorizationConfig));
  }
}
