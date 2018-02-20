package com.jusoft.bookingengine.component.building.api;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class BuildingView {

  private final long id;
  private final long clubId;
  @NonNull
  private final Address address;
  private final String description;
}
