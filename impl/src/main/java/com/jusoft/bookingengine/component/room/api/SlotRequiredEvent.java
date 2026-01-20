package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.publisher.Event;

public record SlotRequiredEvent(long roomId) implements Event {}
