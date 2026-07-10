package com.jusoft.bookingengine.controller.building.api;

import jakarta.validation.constraints.NotNull;

public record CreateBuildingRequest(
        @NotNull Long clubId, String street, String zipCode, String city, String description) {}
