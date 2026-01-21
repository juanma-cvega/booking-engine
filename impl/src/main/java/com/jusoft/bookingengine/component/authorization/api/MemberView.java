package com.jusoft.bookingengine.component.authorization.api;

import java.util.Map;
import java.util.Objects;

public record MemberView(
        long id, long userId, long clubId, Map<Long, MemberBuildingView> buildings) {
    public MemberView {
        Objects.requireNonNull(buildings, "buildings must not be null");
        buildings = Map.copyOf(buildings);
    }
}
