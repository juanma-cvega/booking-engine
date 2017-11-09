package com.jusoft.component.room.api;

import com.jusoft.component.shared.Command;
import lombok.Data;

@Data
public class OpenNextSlotCommand implements Command {

    private final long roomId;

}
