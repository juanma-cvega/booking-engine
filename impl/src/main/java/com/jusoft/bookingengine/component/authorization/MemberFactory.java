package com.jusoft.bookingengine.component.authorization;

import static java.util.stream.Collectors.toMap;

import com.jusoft.bookingengine.component.authorization.api.MemberBuildingView;
import com.jusoft.bookingengine.component.authorization.api.MemberRoomView;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MemberFactory {

    MemberView createFrom(Member member) {
        return MemberView.of(
                member.getId(),
                member.getUserId(),
                member.getClubId(),
                member.getBuildings().values().stream()
                        .map(this::createBuildingHolderFrom)
                        .collect(toMap(MemberBuildingView::getId, building -> building)));
    }

    private MemberBuildingView createBuildingHolderFrom(MemberBuildingAccessConfig building) {
        return MemberBuildingView.of(
                building.getId(),
                building.getRooms().values().stream()
                        .map(this::createRoomHolderFrom)
                        .collect(toMap(MemberRoomView::getId, room -> room)),
                building.getTags());
    }

    private MemberRoomView createRoomHolderFrom(MemberRoomAccessConfig room) {
        return MemberRoomView.of(room.getId(), room.getTagsBySlotStatus());
    }
}
