package com.jusoft.bookingengine.component.building.api;

import lombok.Data;

@Data
public class BuildingView {

  private final long id;
  private final long clubId;
  private final Address address;
  private final String description;
}
