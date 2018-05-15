package com.jusoft.bookingengine.component.instructor.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class CreateInstructorCommand implements Command {

  private final long clubId;
  @NonNull
  private final PersonalInfo personalInfo;

}
