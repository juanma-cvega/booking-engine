package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;

public record JoinRequestCreatedEvent(long joinRequestId, long clubId) implements Event {}
