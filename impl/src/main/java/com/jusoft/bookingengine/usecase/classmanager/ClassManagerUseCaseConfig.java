package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassManagerUseCaseConfig {

    @Autowired private ClassManagerComponent classManagerComponent;

    @Bean
    public CreateClassUseCase createClassUseCase() {
        return new CreateClassUseCase(classManagerComponent);
    }

    @Bean
    public FindClassUseCase findClassUseCase() {
        return new FindClassUseCase(classManagerComponent);
    }

    @Bean
    public FindClassesByBuildingUseCase findClassesByBuildingUseCase() {
        return new FindClassesByBuildingUseCase(classManagerComponent);
    }

    @Bean
    public RemoveClassUseCase removeClassUseCase() {
        return new RemoveClassUseCase(classManagerComponent);
    }

    @Bean
    public AddInstructorUseCase addInstructorUseCase() {
        return new AddInstructorUseCase(classManagerComponent);
    }

    @Bean
    public RemoveInstructorUseCase removeInstructorUseCase() {
        return new RemoveInstructorUseCase(classManagerComponent);
    }

    @Bean
    public RegisterRoomUseCase registerRoomUseCase() {
        return new RegisterRoomUseCase(classManagerComponent);
    }

    @Bean
    public UnregisterRoomUseCase unregisterRoomUseCase() {
        return new UnregisterRoomUseCase(classManagerComponent);
    }
}
