package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;

public record AcceptJoinRequestCommand(long joinRequestId, long clubId, long adminId)
        implements Command {}
