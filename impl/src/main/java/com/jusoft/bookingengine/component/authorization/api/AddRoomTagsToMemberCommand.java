package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class AddRoomTagsToMemberCommand implements Command {

    private final long memberId;
    private final long buildingId;
    private final long roomId;

    @NonNull private final SlotStatus status;

    @NonNull private final List<Tag> tags;
}
