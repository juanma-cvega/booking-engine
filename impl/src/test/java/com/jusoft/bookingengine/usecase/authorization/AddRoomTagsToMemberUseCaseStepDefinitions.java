package com.jusoft.bookingengine.usecase.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class AddRoomTagsToMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private AuthorizationManagerComponent authorizationManagerComponent;

    @Autowired private AddRoomTagsToMemberUseCase addRoomTagsToMemberUseCase;

    @When("^member (.*) is added tag for slot status (.*) to room (.*) in building (.*)$")
    public void member_is_added_tag_for_slot_status_to_room_in_building(
            Long memberId,
            SlotStatus status,
            Long roomId,
            Long buildingId,
            DataTable tagsDataTable) {
        List<Tag> tags = tagsDataTable.row(0).stream().map(Tag::of).collect(Collectors.toList());
        addRoomTagsToMemberUseCase.addRoomTagsToMember(
                new AddRoomTagsToMemberCommand(memberId, buildingId, roomId, status, tags));
    }

    @Then("^member (.*) should have room (\\d+) in building (\\d+) added to its list of buildings$")
    public void member_should_have_room_in_building_added_to_its_list_of_buildings(
            Long memberId, Long roomId, Long buildingId) {
        Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
        assertThat(member).isPresent();
        assertThat(member.get().buildings().get(buildingId).rooms().get(roomId)).isNotNull();
    }

    @Then(
            "^room (.*) in building (.*) of member (.*) should have tag of slot status (.*) in its list of tags$")
    public void room_in_building_of_member_should_have_tag_of_slot_status_in_its_list_of_tags(
            Long roomId,
            Long buildingId,
            Long memberId,
            SlotStatus status,
            DataTable tagsDataTable) {
        List<Tag> tags = tagsDataTable.row(0).stream().map(Tag::of).collect(Collectors.toList());
        Optional<MemberView> member = authorizationManagerComponent.findMemberBy(memberId);
        assertThat(member).isPresent();
        assertThat(member.get().buildings().get(buildingId).rooms().get(roomId).tags().get(status))
                .containsExactlyElementsOf(tags);
    }

    @When(
            "^member (.*) is tried to be added tag for slot status (.*) to room (.*) in building (.*)$")
    public void member_is_tried_to_be_added_tag_for_slot_status_to_room_in_building(
            Long memberId,
            SlotStatus status,
            Long roomId,
            Long buildingId,
            DataTable tagsDataTable) {
        List<Tag> tags = tagsDataTable.row(0).stream().map(Tag::of).collect(Collectors.toList());
        storeException(
                () ->
                        addRoomTagsToMemberUseCase.addRoomTagsToMember(
                                new AddRoomTagsToMemberCommand(
                                        memberId, buildingId, roomId, status, tags)));
    }
}
