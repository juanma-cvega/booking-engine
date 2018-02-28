package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.EnumMap;
import java.util.List;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class RoomView {

  private final long id;
  @NonNull
  private final EnumMap<SlotStatus, List<Tag>> tags;

}
