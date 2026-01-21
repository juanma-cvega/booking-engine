package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import java.util.Objects;

public record AddBuildingTagsToClubCommand(long clubId, long buildingId, List<Tag> tags)
        implements Command {
    public AddBuildingTagsToClubCommand {
        Objects.requireNonNull(tags, "tags must not be null");
        tags = List.copyOf(tags);
    }
}
