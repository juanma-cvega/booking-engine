package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;

public record ClassInstructorRemovedEvent(long classId, long instructorId) implements Event {}
