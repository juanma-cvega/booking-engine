package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class Building {

    private final long id;
    private final long clubId;
    private final Address address;
    private final String description;
}
