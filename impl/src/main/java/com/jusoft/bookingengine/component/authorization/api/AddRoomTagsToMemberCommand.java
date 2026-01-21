package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import java.util.Objects;

public record AddRoomTagsToMemberCommand(
        long memberId, long buildingId, long roomId, SlotStatus status, List<Tag> tags)
        implements Command {
    public AddRoomTagsToMemberCommand {
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(tags, "tags must not be null");
        tags = List.copyOf(tags);
    }
}
