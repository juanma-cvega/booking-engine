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
        return new MemberView(
                member.getId(),
                member.getUserId(),
                member.getClubId(),
                member.getBuildings().values().stream()
                        .map(this::createBuildingHolderFrom)
                        .collect(toMap(MemberBuildingView::id, building -> building)));
    }

    private MemberBuildingView createBuildingHolderFrom(MemberBuildingAccessConfig building) {
        return new MemberBuildingView(
                building.getId(),
                building.getRooms().values().stream()
                        .map(this::createRoomHolderFrom)
                        .collect(toMap(MemberRoomView::id, room -> room)),
                building.getTags());
    }

    private MemberRoomView createRoomHolderFrom(MemberRoomAccessConfig room) {
        return new MemberRoomView(room.getId(), room.getTagsBySlotStatus());
    }
}
