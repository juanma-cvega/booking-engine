package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotUseCase {

  private final SlotManagerComponent slotManagerComponent;
  private final RoomManagerComponent roomManagerComponent;
  private final BuildingManagerComponent buildingManagerComponent;
  private final MemberManagerComponent memberManagerComponent;

  public void reserveSlot(ReserveSlotCommand command) {
    SlotView slotView = slotManagerComponent.find(command.getSlotId());
    RoomView roomView = roomManagerComponent.find(slotView.getRoomId());
    BuildingView buildingView = buildingManagerComponent.find(roomView.getBuildingId());
    if (!memberManagerComponent.isMemberOf(buildingView.getClubId(), command.getUserId())) {
      throw new UserNotMemberException(command.getUserId(), buildingView.getClubId());
    }
    slotManagerComponent.reserveSlot(command.getSlotId(), command.getUserId());
  }
}
