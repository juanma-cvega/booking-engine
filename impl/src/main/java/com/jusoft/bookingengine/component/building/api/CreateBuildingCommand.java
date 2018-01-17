package com.jusoft.bookingengine.component.building.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateBuildingCommand {

  private final long clubId;
  private final Address address;
  private final String description;
}
