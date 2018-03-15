package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class MemberView {

  private final long id;
  private final long userId;
  private final long clubId;
  @NonNull
  private final Map<Long, MemberBuildingView> buildings;
}
