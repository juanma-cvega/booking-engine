package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class AddClassTypeCommand {

  private final long instructorId;
  @NonNull
  private final List<String> classTypes;
}
