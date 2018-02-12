package com.jusoft.bookingengine.component.room.api;

import lombok.Getter;

@Getter
public class RoomWithoutBuildingException extends RuntimeException {

  private static final String MESSAGE = "Cannot create room. Building %s does not exist";

  private final long buildingId;

  public RoomWithoutBuildingException(long buildingId) {
    super(String.format(MESSAGE, buildingId));
    this.buildingId = buildingId;
  }
}
