package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class CreateSlotCommand implements Command {

    private final long roomId;
    private final long buildingId;
    private final long clubId;

    @NonNull private final OpenDate openDate;
}
