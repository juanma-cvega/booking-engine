package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationComponent;
import com.jusoft.bookingengine.component.authorization.api.SlotCoordinates;
import com.jusoft.bookingengine.component.member.BuildingRole;
import com.jusoft.bookingengine.component.member.RoomRole;
import com.jusoft.bookingengine.component.member.SlotRole;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.component.member.api.MemberView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationComponentImpl implements AuthorizationComponent {

  private final MemberManagerComponent memberManagerComponent;

  @Override
  public void canReserveSlot(SlotCoordinates slotCoordinates, Runnable action) {
    MemberView member = memberManagerComponent.find(slotCoordinates.getUserId(), slotCoordinates.getClubId());
    boolean isAllowed = member.getRoles().stream()
      .filter(role -> role instanceof BuildingRole)
      .map(role -> (BuildingRole) role)
      .anyMatch(role -> role.satisfiesFor(slotCoordinates.getBuildingId()));
    if (isAllowed) {
      isAllowed = member.getRoles().stream()
        .filter(role -> role instanceof RoomRole)
        .map(role -> (RoomRole) role)
        .anyMatch(role -> role.satisfiesFor(slotCoordinates.getRoomId()));
    }
    if (isAllowed) {
      isAllowed = member.getRoles().stream()
        .filter(role -> role instanceof SlotRole)
        .map(role -> (SlotRole) role)
        .anyMatch(role -> role.satisfiesFor(slotCoordinates.getSlotType()));
    }
    if (isAllowed) {
      action.run();
    } else {
      throw new NotAuthorizedException();
    }
  }

  @Override
  public void canBid(SlotCoordinates slotCoordinates, Runnable action) {

  }


}
