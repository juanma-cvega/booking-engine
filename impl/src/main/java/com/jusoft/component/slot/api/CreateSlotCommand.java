package com.jusoft.component.slot.api;

import com.jusoft.component.shared.Command;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class CreateSlotCommand implements Command {

    private final long roomId;
    @NonNull
    private final ZonedDateTime startTime;
    @NonNull
    private final ZonedDateTime endTime;

}
