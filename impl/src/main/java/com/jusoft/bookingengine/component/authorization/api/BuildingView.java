package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class BuildingView {

  private final long id;
  @NonNull
  private final Map<Long, RoomView> rooms;
  @NonNull
  private final List<Tag> tags;

  public static BuildingView of(long buildingId) {
    return BuildingView.of(buildingId, new HashMap<>(), new ArrayList<>());
  }
}
