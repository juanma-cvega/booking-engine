package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyRegistrar;
import com.jusoft.bookingengine.usecase.authorization.AuthoriseUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotUseCaseConfig {

  @Autowired
  private RoomManagerComponent roomManagerComponent;
  @Autowired
  private SlotManagerComponent slotManagerComponent;
  @Autowired
  private SchedulerComponent schedulerComponent;
  @Autowired
  private SlotCreationStrategyRegistrar slotCreationStrategyRegistrar;
  @Autowired
  private AuthoriseUserUseCase authoriseUserUseCase;

  @Bean
  public CreateSlotUseCase openNextSlotUseCase() {
    return new CreateSlotUseCase(roomManagerComponent, slotManagerComponent);
  }

  @Bean
  public ScheduleNextSlotUseCase scheduleNextSlotUseCase() {
    return new ScheduleNextSlotUseCase(roomManagerComponent, schedulerComponent, slotCreationStrategyRegistrar);
  }

  @Bean
  public MakeSlotAvailableUseCase makeSlotAvailableUseCase() {
    return new MakeSlotAvailableUseCase(slotManagerComponent);
  }

  @Bean
  public ReserveSlotForAuctionWinnerUseCase reserveSlotForAuctionWinnerUseCase() {
    return new ReserveSlotForAuctionWinnerUseCase(slotManagerComponent);
  }

  @Bean
  public ReserveSlotUseCase reserveSlotUseCase() {
    return new ReserveSlotUseCase(authoriseUserUseCase, slotManagerComponent);
  }
}
