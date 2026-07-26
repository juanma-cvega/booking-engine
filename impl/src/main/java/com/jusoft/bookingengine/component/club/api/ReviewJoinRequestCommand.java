package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;

public record ReviewJoinRequestCommand(
        long joinRequestId, long clubId, long adminId, Decision decision) implements Command {}
