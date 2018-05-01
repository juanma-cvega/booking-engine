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
  public AddClassTimetableUseCase addClassTimetableUseCase() {
    return new AddClassTimetableUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public RemoveClassTimetableUseCase removeClassTimetableUseCase() {
    return new RemoveClassTimetableUseCase(slotLifeCycleManagerComponent);
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
  public ReplaceSlotsTimetableUseCase replaceSlotsTimetableUseCase() {
    return new ReplaceSlotsTimetableUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public FindNextSlotStateUseCase findNextSlotStateUseCase() {
    return new FindNextSlotStateUseCase(slotLifeCycleManagerComponent);
  }

  @Bean
  public NotifyOfSlotReservationUseCase notifyOfSlotReservationUseCase() {
    return new NotifyOfSlotReservationUseCase(slotLifeCycleManagerComponent);
  }
}
