package com.jusoft.bookingengine.controller.building.api;

import com.jusoft.bookingengine.component.building.api.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBuildingRequest(
        @NotNull Long clubId,
        @NotBlank String street,
        @NotBlank String zipCode,
        @NotBlank String city,
        String description) {

    public Address toAddress() {
        return Address.of(street, zipCode, city);
    }
}
