package com.jusoft.bookingengine.component.building.api;

import java.util.Objects;

public record BuildingView(long id, long clubId, Address address, String description) {
    public BuildingView {
        Objects.requireNonNull(address, "address must not be null");
    }
}
