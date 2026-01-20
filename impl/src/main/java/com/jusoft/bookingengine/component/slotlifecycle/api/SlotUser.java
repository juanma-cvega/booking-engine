package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class SlotUser {

    public enum UserType {
        PERSON,
        CLASS
    }

    private final long id;
    private final UserType userType;
}
