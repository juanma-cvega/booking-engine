package com.jusoft.bookingengine.usecase.instructortimetables;

import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstructorTimetablesUseCaseConfig {

  @Autowired
  private InstructorTimetablesManagerComponent instructorTimetablesManagerComponent;

  @Bean
  public AddTimetableToInstructorUseCase addTimetableToInstructorUseCase() {
    return new AddTimetableToInstructorUseCase(instructorTimetablesManagerComponent);
  }

  @Bean
  public CreateInstructorTimetablesUseCase createInstructorTimetablesUseCase() {
    return new CreateInstructorTimetablesUseCase(instructorTimetablesManagerComponent);
  }

  @Bean
  public DeleteInstructorTimetablesUseCase deleteInstructorTimetablesUseCase() {
    return new DeleteInstructorTimetablesUseCase(instructorTimetablesManagerComponent);
  }

  @Bean
  public FindInstructorTimetablesUseCase findInstructorTimetablesUseCase() {
    return new FindInstructorTimetablesUseCase(instructorTimetablesManagerComponent);
  }

  @Bean
  public RemoveTimetableFromInstructorUseCase removeTimetableFromInstructorUseCase() {
    return new RemoveTimetableFromInstructorUseCase(instructorTimetablesManagerComponent);
  }
}
