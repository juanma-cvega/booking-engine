package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;

public record DenyJoinRequestCommand(long joinRequestId, long clubId, long adminId)
        implements Command {}
