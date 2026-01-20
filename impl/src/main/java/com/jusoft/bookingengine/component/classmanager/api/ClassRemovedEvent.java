package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class ClassRemovedEvent implements Event {

    private final long classId;
}
