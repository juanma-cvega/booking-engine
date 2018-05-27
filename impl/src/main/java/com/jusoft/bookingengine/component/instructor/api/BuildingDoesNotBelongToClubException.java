package com.jusoft.bookingengine.component.instructor.api;

import lombok.Getter;

@Getter
public class BuildingDoesNotBelongToClubException extends RuntimeException {

  private static final long serialVersionUID = -1295610481097835006L;

  private static final String MESSAGE = "Cannot add instructor %s to building %s. Building does not belong to club %s";

  private final long instructorId;
  private final long clubId;
  private final long buildingId;

  public BuildingDoesNotBelongToClubException(long buildingId, long clubId, long instructorId) {
    super(String.format(MESSAGE, instructorId, buildingId, clubId));
    this.instructorId = instructorId;
    this.clubId = clubId;
    this.buildingId = buildingId;
  }
}
