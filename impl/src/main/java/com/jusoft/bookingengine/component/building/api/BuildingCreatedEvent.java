package com.jusoft.bookingengine.component.building.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class BuildingCreatedEvent implements Event {

    private final long buildingId;
    private final Address address;
    private final String description;
}
