package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationComponent;
import com.jusoft.bookingengine.component.authorization.api.SlotCoordinates;
import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotType;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotUseCase {

  private final SlotManagerComponent slotManagerComponent;
  private final RoomManagerComponent roomManagerComponent;
  private final BuildingManagerComponent buildingManagerComponent;
  private final AuthorizationComponent authorizationComponent;

  public void reserveSlot(long slotId, long userId) {
    SlotView slotView = slotManagerComponent.find(slotId);
    RoomView roomView = roomManagerComponent.find(slotView.getRoomId());
    BuildingView buildingView = buildingManagerComponent.find(roomView.getBuildingId());
    authorizationComponent.canReserveSlot(SlotCoordinates.of(
      userId,
      buildingView.getClubId(),
      buildingView.getId(),
      roomView.getId(),
      SlotType.NORMAL
    ), () -> slotManagerComponent.reserveSlot(slotId, userId));
  }
}
