package com.jusoft.bookingengine.component.building.api;

public record CreateBuildingCommand(long clubId, Address address, String description) {}
