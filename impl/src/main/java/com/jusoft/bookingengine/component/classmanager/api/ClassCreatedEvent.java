package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;
import java.util.List;
import lombok.Data;

@Data(staticConstructor = "of")
public class ClassCreatedEvent implements Event {

    private final long classId;
    private final String description;
    private final List<Long> instructorsId;
    private final String classType;
}
