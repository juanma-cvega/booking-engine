package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class AllBuildingsAccessRole implements BuildingRole {

  private static final AllBuildingsAccessRole INSTANCE = new AllBuildingsAccessRole();

  @Override
  public boolean satisfiesFor(long id) {
    return true;
  }

  public static AllBuildingsAccessRole getInstance() {
    return INSTANCE;
  }
}
