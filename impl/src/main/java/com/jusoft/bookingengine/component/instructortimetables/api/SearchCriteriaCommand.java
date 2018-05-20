package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class SearchCriteriaCommand {

  private final long instructorId;
  @NonNull
  private final List<Long> buildingsId;
  @NonNull
  private final List<Long> roomsId;
  @NonNull
  private final List<String> classTypes;
}
