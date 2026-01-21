package com.jusoft.bookingengine.component.authorization.api;

import java.util.Map;
import java.util.Objects;

public record ClubView(long id, Map<Long, ClubBuildingView> buildings) {
    public ClubView {
        Objects.requireNonNull(buildings, "buildings must not be null");
        buildings = Map.copyOf(buildings);
    }
}
