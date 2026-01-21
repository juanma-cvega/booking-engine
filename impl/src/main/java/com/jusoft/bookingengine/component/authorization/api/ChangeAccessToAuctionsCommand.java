package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;

public record ChangeAccessToAuctionsCommand(long memberId, long buildingId, long roomId)
        implements Command {}
