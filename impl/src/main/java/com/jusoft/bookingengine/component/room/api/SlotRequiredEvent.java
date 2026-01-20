package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotRequiredEvent implements Event {

    private final long roomId;
}
