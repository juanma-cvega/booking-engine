package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.building.api.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuildingFixtures {

  public static final long BUILDING_ID = 125L;
  public static final String CITY = "any city";
  public static final String ZIP_CODE = "any zip code";
  public static final String STREET = "any street";
  public static final String BUILDING_DESCRIPTION = "building description";
  public static final Address ADDRESS = Address.of(STREET, ZIP_CODE, CITY);
}
