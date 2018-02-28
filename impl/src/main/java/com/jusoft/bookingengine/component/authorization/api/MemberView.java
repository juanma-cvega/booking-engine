package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class MemberView {

  private final long id;
  @NonNull
  private final Map<Long, BuildingView> buildings;
}
