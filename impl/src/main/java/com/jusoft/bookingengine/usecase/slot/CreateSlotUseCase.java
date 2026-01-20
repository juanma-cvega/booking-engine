package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSlotUseCase {

    private final RoomManagerComponent roomManagerComponent;
    private final SlotManagerComponent slotManagerComponent;

    public SlotView createSlotFor(long roomId) {
        NextSlotConfig nextSlotOpenDate =
                slotManagerComponent
                        .findLastCreatedFor(roomId)
                        .map(
                                slot ->
                                        roomManagerComponent.findNextSlotOpenDate(
                                                slot.getOpenDate().getEndTime(), roomId))
                        .orElse(roomManagerComponent.findFirstSlotOpenDate(roomId));

        return slotManagerComponent.create(
                CreateSlotCommand.of(
                        roomId,
                        nextSlotOpenDate.getBuildingId(),
                        nextSlotOpenDate.getClubId(),
                        nextSlotOpenDate.getOpenDate()));
    }
}
