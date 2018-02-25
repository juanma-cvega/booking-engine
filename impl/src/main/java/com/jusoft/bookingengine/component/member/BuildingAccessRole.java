package com.jusoft.bookingengine.component.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BuildingAccessRole implements BuildingRole {

  private final long buildingId;

  @Override
  public boolean satisfiesFor(long id) {
    return buildingId == id;
  }
}
