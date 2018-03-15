package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.usecase.authorization.AuthoriseUserUseCase;
import com.jusoft.bookingengine.usecase.authorization.AuthoriseUserUseCaseCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReserveSlotUseCase {

  private final AuthoriseUserUseCase authoriseUserUseCase;
  private final SlotManagerComponent slotManagerComponent;

  public void reserveSlot(long slotId, long userId) {
    SlotView slot = slotManagerComponent.find(slotId);
    authoriseUserUseCase.isAuthorised(AuthoriseUserUseCaseCommand.of(
      slot.getBuildingId(),
      slot.getRoomId(),
      slot.getCreationTime(),
      slot.getClubId(),
      userId));
    slotManagerComponent.reserveSlot(slotId, userId);
  }
}
