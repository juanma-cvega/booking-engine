package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSlotUseCase {

  private final RoomManagerComponent roomManagerComponent;
  private final SlotManagerComponent slotManagerComponent;

  public SlotView createSlotFor(OpenNextSlotCommand command) {
    NextSlotConfig nextSlotOpenDate = slotManagerComponent.findLastCreatedFor(command.getRoomId())
      .map(slot -> roomManagerComponent.findNextSlotOpenDate(slot.getOpenDate().getEndTime(), command.getRoomId()))
      .orElse(roomManagerComponent.findFirstSlotOpenDate(command.getRoomId()));

    return slotManagerComponent.create(new CreateSlotCommand(command.getRoomId(),
      nextSlotOpenDate.getOpenDate(),
      nextSlotOpenDate.getState()));
  }
}
