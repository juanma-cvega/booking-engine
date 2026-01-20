package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Event;

public record RoomUnregisteredForClassEvent(long classId, long roomId) implements Event {}
