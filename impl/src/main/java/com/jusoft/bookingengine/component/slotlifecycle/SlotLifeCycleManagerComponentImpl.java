package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
class SlotLifeCycleManagerComponentImpl implements SlotLifeCycleManagerComponent {

  private final SlotLifeCycleManagerRepository repository;
  private final MessagePublisher messagePublisher;
  private final SlotLifeCycleEventFactory eventFactory;
  private final Clock clock;

  @Override
  public SlotLifeCycleManagerView createFrom(long roomId, SlotsTimetable slotsTimetable) {
    SlotLifeCycleManager slotLifeCycleManager = new SlotLifeCycleManager(roomId, slotsTimetable);
    repository.save(slotLifeCycleManager);
    return createFrom(slotLifeCycleManager);
  }

  @Override
  public SlotLifeCycleManagerView find(long roomId) {
    SlotLifeCycleManager slotLifeCycleManager = findOrFail(roomId);
    return createFrom(slotLifeCycleManager);
  }

  @Override
  public void replaceSlotsTimetableWith(long roomId, SlotsTimetable slotsTimetable) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.replaceSlotsTimetableWith(slotsTimetable, clock),
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
  public void addClassTimetableTo(long roomId, ClassTimetable classTimetable) {
    repository.execute(roomId,
      slotLifeCycleManager -> slotLifeCycleManager.addClass(classTimetable, clock),
      supplySlotLifeCycleManagerNotFoundException(roomId));
  }

  @Override
  public void removeClassTimetableFrom(long roomId, long classId) {
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
    NextSlotState nextSlotState = slotLifeCycleManager.nextStateFor(slotId, slotStartTime, clock);
    messagePublisher.publish(eventFactory.getEventFrom(nextSlotState));
  }

  @Override
  public void notifyOfSlotReservation(long slotId, SlotUser slotUser) {
    messagePublisher.publish(eventFactory.getEventFrom(slotId, slotUser));
  }

  private SlotLifeCycleManager findOrFail(long roomId) {
    return repository.find(roomId).orElseThrow(() -> new SlotLifeCycleManagerNotFoundException(roomId));
  }

  private List<SlotLifeCycleManagerView> createFrom(List<SlotLifeCycleManager> slotLifeCycleManagers) {
    return slotLifeCycleManagers.stream().map(this::createFrom).collect(toList());
  }

  private SlotLifeCycleManagerView createFrom(SlotLifeCycleManager slotLifeCycleManager) {
    return SlotLifeCycleManagerView.of(
      slotLifeCycleManager.getRoomId(),
      slotLifeCycleManager.getSlotsTimetable(),
      slotLifeCycleManager.getAuctionConfigInfo(),
      slotLifeCycleManager.getPreReservations(),
      slotLifeCycleManager.getClassesTimeTable());
  }
}
