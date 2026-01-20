package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;

public record ClassInstructorAddedEvent(long classId, long newInstructorId) implements Event {}
