package com.jusoft.bookingengine.component.slot.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class SlotUser {

    private final long userId;
    private final String userType;
}
