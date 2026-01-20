package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;
import java.util.List;

public record ClassCreatedEvent(
        long classId, String description, List<Long> instructorsId, String classType)
        implements Event {}
