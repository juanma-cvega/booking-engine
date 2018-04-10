package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotLifeCycleManagerUseCaseConfig {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Bean
  public CreateSlotLifeCycleManagerUseCase createSlotLifeCycleManagerUseCase() {
    return new CreateSlotLifeCycleManagerUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public AddClassConfigUseCase addClassConfigUseCase() {
    return new AddClassConfigUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public RemoveClassConfigUseCase removeClassConfigUseCase() {
    return new RemoveClassConfigUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public AddPreReservationUseCase addPreReservationUseCase() {
    return new AddPreReservationUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public RemovePreReservationUseCase removePreReservationUseCase() {
    return new RemovePreReservationUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public ModifyAuctionConfigUseCase modifyAuctionConfigUseCase() {
    return new ModifyAuctionConfigUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public ReplaceSlotValidationUseCase replaceSlotValidationUseCase() {
    return new ReplaceSlotValidationUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public FindNextSlotStateUseCase findNextSlotStateUseCase() {
    return new FindNextSlotStateUseCase(slotLifeCycleManagerComponent);
  }
}
