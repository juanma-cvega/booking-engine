package com.jusoft.bookingengine.component.authorization.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
@EqualsAndHashCode(of = "id")
public class ClubRoomView {

  private final long id;
  @NonNull
  private final Map<SlotStatus, List<Tag>> tags;
  @NonNull
  private final SlotAuthorizationConfig slotAuthorizationConfig;

}
