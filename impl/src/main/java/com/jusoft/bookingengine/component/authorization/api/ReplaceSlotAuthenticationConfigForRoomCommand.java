package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class ReplaceSlotAuthenticationConfigForRoomCommand implements Command {

    private final long clubId;
    private final long buildingId;
    private final long roomId;

    @NonNull private final SlotAuthorizationConfig slotAuthenticationConfig;
}
