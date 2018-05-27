package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.function.Supplier;

@AllArgsConstructor
class InstructorFactory {

  private final Supplier<Long> idSupplier;

  Instructor createFrom(CreateInstructorCommand command) {
    return new Instructor(
      idSupplier.get(),
      command.getClubId(),
      command.getPersonalInfo());
  }

  InstructorView createFrom(Instructor instructor) {
    return InstructorView.of(
      instructor.getId(),
      instructor.getClubId(),
      new HashSet<>(instructor.getRegisteredBuildingsId()),
      instructor.getPersonalInfo(),
      new HashSet<>(instructor.getSupportedClassTypes()));
  }
}
