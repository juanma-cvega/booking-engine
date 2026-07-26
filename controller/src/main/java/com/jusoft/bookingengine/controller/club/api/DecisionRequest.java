package com.jusoft.bookingengine.controller.club.api;

import com.jusoft.bookingengine.component.club.api.Decision;

public enum DecisionRequest {
    ACCEPTED,
    DENIED;

    public Decision toDecision() {
        return switch (this) {
            case ACCEPTED -> Decision.ACCEPTED;
            case DENIED -> Decision.DENIED;
        };
    }
}
