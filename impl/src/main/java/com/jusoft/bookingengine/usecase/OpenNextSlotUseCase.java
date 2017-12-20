package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenNextSlotUseCase {

  private final RoomComponent roomComponent;
  private final SlotComponent slotComponent;

  public SlotView openNextSlotFor(long roomId) {
    OpenDate nextSlotOpenDate = slotComponent.findLastCreatedFor(roomId)
      .map(slot -> roomComponent.findNextSlotOpenDate(slot.getEndDate(), roomId))
      .orElse(roomComponent.findFirstSlotOpenDate(roomId));

    return slotComponent.create(new CreateSlotCommand(roomId, nextSlotOpenDate.getStartTime(), nextSlotOpenDate.getEndTime()));
  }
}
