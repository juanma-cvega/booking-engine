package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class AddRoomTagsToClubCommand implements Command {

    private final long clubId;
    private final long buildingId;
    private final long roomId;

    @NonNull private final SlotStatus status;

    @NonNull private final List<Tag> tags;
}
