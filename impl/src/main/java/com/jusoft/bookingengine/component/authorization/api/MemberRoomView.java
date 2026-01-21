package com.jusoft.bookingengine.component.authorization.api;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record MemberRoomView(long id, Map<SlotStatus, List<Tag>> tags) {
    public MemberRoomView {
        Objects.requireNonNull(tags, "tags must not be null");
        tags = Map.copyOf(tags);
    }
}
