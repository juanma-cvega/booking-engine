package com.jusoft.bookingengine.controller.building.api;

public record BuildingResource(
        long buildingId,
        long clubId,
        String street,
        String zipCode,
        String city,
        String description) {}
