package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class RemoveClassTypesCommand implements Command {

  private final long instructorId;
  @NonNull
  private final List<String> classTypes;
}
