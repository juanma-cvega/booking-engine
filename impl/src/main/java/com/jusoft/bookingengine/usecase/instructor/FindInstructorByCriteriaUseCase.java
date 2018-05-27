package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.component.instructor.api.SearchCriteriaCommand;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindInstructorByCriteriaUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public List<InstructorView> findByCriteria(SearchCriteriaCommand command) {
    return instructorManagerComponent.findBy(command);
  }
}
