package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
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

  public SlotView openNextSlotFor(OpenNextSlotCommand command) {
    OpenDate nextSlotOpenDate = slotComponent.findLastCreatedFor(command.getRoomId())
      .map(slot -> roomComponent.findNextSlotOpenDate(slot.getEndDate(), command.getRoomId()))
      .orElse(roomComponent.findFirstSlotOpenDate(command.getRoomId()));

    return slotComponent.create(new CreateSlotCommand(command.getRoomId(), command.getClubId(), nextSlotOpenDate.getStartTime(), nextSlotOpenDate.getEndTime()));
  }
}
