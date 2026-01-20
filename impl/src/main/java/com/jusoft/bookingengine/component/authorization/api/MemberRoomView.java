package com.jusoft.bookingengine.component.authorization.api;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class MemberRoomView {

    private final long id;

    @NonNull private final Map<SlotStatus, List<Tag>> tags;
}
