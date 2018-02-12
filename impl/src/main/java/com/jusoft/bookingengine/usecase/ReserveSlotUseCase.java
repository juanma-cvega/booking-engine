package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.member.api.MemberComponent;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotUseCase {

  private final SlotComponent slotComponent;
  private final RoomComponent roomComponent;
  private final BuildingComponent buildingComponent;
  private final MemberComponent memberComponent;

  public void reserveSlot(ReserveSlotCommand command) {
    SlotView slotView = slotComponent.find(command.getSlotId());
    RoomView roomView = roomComponent.find(slotView.getRoomId());
    BuildingView buildingView = buildingComponent.find(roomView.getBuildingId());
    if (!memberComponent.isMemberOf(buildingView.getClubId(), command.getUserId())) {
      throw new UserNotMemberException(command.getUserId(), buildingView.getClubId());
    }
    slotComponent.reserveSlot(command);
  }
}
