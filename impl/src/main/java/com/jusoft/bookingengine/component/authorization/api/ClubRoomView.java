package com.jusoft.bookingengine.component.authorization.api;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ClubRoomView(
        long id, Map<SlotStatus, List<Tag>> tags, SlotAuthorizationConfig slotAuthorizationConfig) {
    public ClubRoomView {
        Objects.requireNonNull(tags, "tags must not be null");
        Objects.requireNonNull(slotAuthorizationConfig, "slotAuthorizationConfig must not be null");
        tags = Map.copyOf(tags);
    }
}
