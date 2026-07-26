package com.jusoft.bookingengine.controller.building.api;

import com.jusoft.bookingengine.component.building.api.Address;
import jakarta.validation.constraints.NotNull;

public record CreateBuildingRequest(
        @NotNull Long clubId, String street, String zipCode, String city, String description) {

    public Address toAddress() {
        return Address.of(street, zipCode, city);
    }
}
