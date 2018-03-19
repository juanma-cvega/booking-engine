package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;
  private final SlotManagerComponent slotManagerComponent;

  public void reserveSlot(long slotId, long userId) {
    SlotView slot = slotManagerComponent.find(slotId);
    authorizationManagerComponent.authorizeReserveSlot(AuthorizeCommand.of(
      userId,
      slot.getRoomId(),
      slot.getBuildingId(),
      slot.getClubId(),
      slot.getCreationTime()
    ));
    slotManagerComponent.reserveSlot(slotId, userId);
  }
}
