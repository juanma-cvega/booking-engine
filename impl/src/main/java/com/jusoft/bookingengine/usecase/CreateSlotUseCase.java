package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSlotUseCase {

  private final RoomComponent roomComponent;
  private final SlotComponent slotComponent;

  public SlotView createSlotFor(OpenNextSlotCommand command) {
    NextSlotConfig nextSlotOpenDate = slotComponent.findLastCreatedFor(command.getRoomId())
      .map(slot -> roomComponent.findNextSlotOpenDate(slot.getOpenDate().getEndTime(), command.getRoomId()))
      .orElse(roomComponent.findFirstSlotOpenDate(command.getRoomId()));

    return slotComponent.create(new CreateSlotCommand(command.getRoomId(),
      nextSlotOpenDate.getOpenDate(),
      nextSlotOpenDate.getState()));
  }
}
