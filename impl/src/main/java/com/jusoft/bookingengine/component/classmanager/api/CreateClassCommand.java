package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class CreateClassCommand implements Command {

  private final long buildingId;
  @NonNull
  private final String description;
  @NonNull
  private final String type;
  @NonNull
  private final List<Long> instructorsId;
}
