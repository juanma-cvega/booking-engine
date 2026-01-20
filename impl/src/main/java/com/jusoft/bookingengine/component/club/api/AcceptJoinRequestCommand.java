package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class AcceptJoinRequestCommand implements Command {

    private final long joinRequestId;
    private final long clubId;
    private final long adminId;
}
