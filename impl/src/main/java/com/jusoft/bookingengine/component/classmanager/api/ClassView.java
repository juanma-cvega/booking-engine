package com.jusoft.bookingengine.component.classmanager.api;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data(staticConstructor = "of")
public class ClassView {

  private final long id;
  private final long buildingId;
  @NonNull
  private final String description;
  private final List<Long> instructorsId;
  @NonNull
  private final String classType;
  @NonNull
  private final List<Long> roomsRegistered;

  public List<Long> getRoomsRegistered() {
    return new ArrayList<>(roomsRegistered);
  }
}
