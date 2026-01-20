package com.jusoft.bookingengine.component.building.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class CreateBuildingCommand {

    private final long clubId;
    private final Address address;
    private final String description;
}
