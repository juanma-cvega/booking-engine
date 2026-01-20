package com.jusoft.bookingengine.component.club.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class JoinRequest {

    private final long id;
    private final long userId;
}
