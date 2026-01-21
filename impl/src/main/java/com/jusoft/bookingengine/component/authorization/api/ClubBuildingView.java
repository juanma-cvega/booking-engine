package com.jusoft.bookingengine.component.authorization.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ClubBuildingView(long id, Map<Long, ClubRoomView> rooms, List<Tag> tags) {
    public ClubBuildingView {
        Objects.requireNonNull(rooms, "rooms must not be null");
        Objects.requireNonNull(tags, "tags must not be null");
        rooms = Map.copyOf(rooms);
        tags = List.copyOf(tags);
    }

    public ClubBuildingView(long buildingId) {
        this(buildingId, new HashMap<>(), List.of());
    }
}
