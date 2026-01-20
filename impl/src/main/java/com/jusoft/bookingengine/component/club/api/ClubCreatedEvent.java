package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Event;

public record ClubCreatedEvent(long clubId) implements Event {}
