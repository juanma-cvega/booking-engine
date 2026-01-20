package com.jusoft.bookingengine.component.club.api;

import java.util.Set;
import lombok.Data;

@Data(staticConstructor = "of")
public class ClubView {

    private final long id;
    private final String name;
    private final String description;
    private final Set<Long> admins;
}
