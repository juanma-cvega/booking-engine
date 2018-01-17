package com.jusoft.bookingengine.component.building.api;

import lombok.Getter;

@Getter
public class BuildingNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Building %s does not exist";

  private final long buildingId;

  public BuildingNotFoundException(long buildingId) {
    super(String.format(MESSAGE, buildingId));
    this.buildingId = buildingId;
  }
}
