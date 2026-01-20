package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;

public record ClassRemovedEvent(long classId) implements Event {}
