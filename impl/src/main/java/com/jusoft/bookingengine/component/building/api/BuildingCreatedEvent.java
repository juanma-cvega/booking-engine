package com.jusoft.bookingengine.component.building.api;

import com.jusoft.bookingengine.publisher.Event;

public record BuildingCreatedEvent(long buildingId, Address address, String description)
        implements Event {}
