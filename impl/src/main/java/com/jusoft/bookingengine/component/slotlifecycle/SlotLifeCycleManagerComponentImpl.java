package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfig;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@AllArgsConstructor
class SlotLifeCycleManagerComponentImpl implements SlotLifeCycleManagerComponent {

  private final SlotLifeCycleManagerRepository repository;
  private final MessagePublisher messagePublisher;
  private final SlotLifeCycleEventFactory factory;
  private final Clock clock;

  @Override
  public SlotLifeCycleManagerView createFrom(long roomId, SlotValidationInfo slotValidationInfo) {
    SlotLifeCycleManager slotLifeCycleManager = new SlotLifeCycleManager(roomId, slotValidationInfo);
    repository.save(slotLifeCycleManager);
    return createFrom(slotLifeCycleManager);
  }

  @Override
  public SlotLifeCycleManagerView find(long roomId) {
    SlotLifeCycleManager slotLifeCycleManager = findOrFail(roomId);
    return createFrom(slotLifeCycleManager);
  }

  private SlotLifeCycleManagerView createFrom(SlotLifeCycleManager slotLifeCycleManager) {
    return SlotLifeCycleManagerView.of(
      slotLifeCycleManager.getRoomId(),
      slotLifeCycleManager.getSlotValidationInfo(),
      slotLifeCycleManager.getAuctionConfigInfo(),
      slotLifeCycleManager.getPreReservations(),
      slotLifeCycleManager.getClassesConfig());
  }

  @Override
  public void replaceSlotValidationWith(long roomId, SlotValidationInfo slotValidationInfo) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.replaceSlotValidationWith(slotValidationInfo),
      () -> new SlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void modifyAuctionConfigFor(long roomId, AuctionConfigInfo auctionConfigInfo) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.replaceAuctionConfigWith(auctionConfigInfo),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  private Supplier<RuntimeException> supplySlotLifeCycleManagerNotFoundException(long roomId) {
    return () -> new SlotLifeCycleManagerNotFoundException(roomId);
  }

  @Override
  public void addClassConfigTo(long roomId, ClassConfig classConfig) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.addClass(classConfig),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void removeClassConfigFrom(long roomId, long classId) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.removeClass(classId),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void addPreReservation(long roomId, PreReservation preReservation) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.addPreReservation(preReservation, clock),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void removePreReservation(long roomId, ZonedDateTime slotStartTime) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.removePreReservation(slotStartTime),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void findNextSlotStateFor(long slotId, long roomId, ZonedDateTime slotStartTime) {
    SlotLifeCycleManager slotLifeCycleManager = repository.find(roomId).orElseThrow(supplySlotLifeCycleManagerNotFoundException(roomId));
    NextSlotState nextSlotState = slotLifeCycleManager.nextStateFor(slotId, slotStartTime);
    messagePublisher.publish(factory.getEventFrom(nextSlotState));
  }

  private SlotLifeCycleManager findOrFail(long roomId) {
    return repository.find(roomId).orElseThrow(() -> new SlotLifeCycleManagerNotFoundException(roomId));
  }
}
