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
public class MemberBuildingView {

  private final long id;
  @NonNull
  private final Map<Long, MemberRoomView> rooms;
  @NonNull
  private final List<Tag> tags;

  public static MemberBuildingView of(long buildingId) {
    return MemberBuildingView.of(buildingId, new HashMap<>(), new ArrayList<>());
  }
}
