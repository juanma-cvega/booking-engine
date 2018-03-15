package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AddRoomTagsToMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private AddRoomTagsToMemberUseCase addRoomTagsToMemberUseCase;

  public AddRoomTagsToMemberUseCaseStepDefinitions() {
    When("^member (.*) is added tag for slot status (.*) to room (\\d+) in building (\\d+)$", (Long memberId, SlotStatus status, Long roomId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      addRoomTagsToMemberUseCase.addRoomTagsToMember(AddRoomTagsToMemberCommand.of(memberId, buildingId, roomId, status, tags));
    });
    Then("^member (.*) should have room (\\d+) in building (\\d+) added to its list of buildings$", (Long memberId, Long roomId, Long buildingId) -> {
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getBuildings().get(buildingId).getRooms().get(roomId)).isNotNull();
    });
    Then("^room (\\d+) in building (\\d+) of member (\\d+) should have tag of slot status (.*) in its list of tags$", (Long roomId, Long buildingId, Long memberId, SlotStatus status, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
      assertThat(member).isPresent();
      assertThat(member.get().getBuildings().get(buildingId).getRooms().get(roomId).getTags().get(status)).containsExactlyElementsOf(tags);
    });
    When("^member (.*) is tried to be added tag for slot status (.*) to room (\\d+) in building (\\d+)$", (Long memberId, SlotStatus status, Long roomId, Long buildingId, DataTable tagsDataTable) -> {
      List<Tag> tags = tagsDataTable.asList(String.class).stream().map(Tag::of).collect(Collectors.toList());
      storeException(() -> addRoomTagsToMemberUseCase.addRoomTagsToMember(AddRoomTagsToMemberCommand.of(memberId, buildingId, roomId, status, tags)));
    });
  }
}
