package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstructorUseCaseConfig {

  @Autowired
  private InstructorManagerComponent instructorManagerComponent;

  @Bean
  public AddClassTypeToInstructorUseCase addClassTypeToInstructorUseCase() {
    return new AddClassTypeToInstructorUseCase(instructorManagerComponent);
  }

  @Bean
  public CreateInstructorUseCase createInstructorUseCase() {
    return new CreateInstructorUseCase(instructorManagerComponent);
  }

  @Bean
  public FindInstructorByCriteriaUseCase findInstructorByCriteriaUseCase() {
    return new FindInstructorByCriteriaUseCase(instructorManagerComponent);
  }

  @Bean
  public FindInstructorUseCase findInstructorUseCase() {
    return new FindInstructorUseCase(instructorManagerComponent);
  }

  @Bean
  public RegisterInstructorWithBuildingUseCase registerInstructorOnBuildingUseCase() {
    return new RegisterInstructorWithBuildingUseCase(instructorManagerComponent);
  }

  @Bean
  public RemoveClassTypesUseCase removeClassTypesUseCase() {
    return new RemoveClassTypesUseCase(instructorManagerComponent);
  }

  @Bean
  public UnregisterInstructorFromBuildingUseCase unregisterInstructorFromBuildingUseCase() {
    return new UnregisterInstructorFromBuildingUseCase(instructorManagerComponent);
  }

}
