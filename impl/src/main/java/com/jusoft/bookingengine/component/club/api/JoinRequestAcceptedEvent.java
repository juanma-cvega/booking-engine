package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;

public record JoinRequestAcceptedEvent(long accessRequestId, long userId, long clubId)
        implements Event {}
