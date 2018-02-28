package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AddRoomTagsToClubUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddRoomTagsToClubUseCase addRoomTagsToClubUseCase;

  public AddRoomTagsToClubUseCaseStepDefinitions() {
    When("^club (\\d+) is added tag for slot status (.*) to room (\\d+) in building (\\d+)$", (Long clubId, SlotStatus status, Long roomId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      addRoomTagsToClubUseCase.addRoomTagsToClub(AddRoomTagsToClubCommand.of(clubId, buildingId, roomId, status, tags));
    });
    Then("^club (\\d+) should have room (\\d+) in building (\\d+) added to its list of buildings$", (Long clubId, Long roomId, Long buildingId) -> {
      Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings().get(buildingId).getRooms().get(roomId)).isNotNull();
    });
    Then("^room (\\d+) in building (\\d+) of club (\\d+) should have tag of slot status (.*) in its list of tags$", (Long roomId, Long buildingId, Long clubId, SlotStatus status, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      Optional<ClubView> club = authorizationManagerComponent.findClubBy(clubId);
      assertThat(club).isPresent();
      assertThat(club.get().getBuildings().get(buildingId).getRooms().get(roomId).getTags().get(status)).containsExactlyElementsOf(tags);
    });
    When("^club (.*) is tried to be added tag for slot status (.*) to room (.*) in building (.*)$", (Long clubId, SlotStatus status, Long roomId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      storeException(() -> addRoomTagsToClubUseCase.addRoomTagsToClub(AddRoomTagsToClubCommand.of(clubId, buildingId, roomId, status, tags)));
    });
  }
}
