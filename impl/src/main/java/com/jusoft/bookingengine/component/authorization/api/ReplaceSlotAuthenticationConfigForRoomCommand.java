package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.Objects;

public record ReplaceSlotAuthenticationConfigForRoomCommand(
        long clubId, long buildingId, long roomId, SlotAuthorizationConfig slotAuthenticationConfig)
        implements Command {
    public ReplaceSlotAuthenticationConfigForRoomCommand {
        Objects.requireNonNull(
                slotAuthenticationConfig, "slotAuthenticationConfig must not be null");
    }
}
