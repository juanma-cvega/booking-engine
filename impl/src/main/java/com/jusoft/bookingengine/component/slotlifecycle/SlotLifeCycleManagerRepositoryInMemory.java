package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfig;
import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
public class SlotLifeCycleManagerRepositoryInMemory implements SlotLifeCycleManagerRepository {

  private final Map<Long, SlotLifeCycleManager> store;
  private final Lock lock;

  @Override
  public void save(SlotLifeCycleManager slotLifeCycleManager) {
    store.put(slotLifeCycleManager.getRoomId(), slotLifeCycleManager);
  }

  @Override
  public Optional<SlotLifeCycleManager> find(long roomId) {
    return Optional.ofNullable(store.get(roomId)).map(this::copyOf);
  }

  private SlotLifeCycleManager copyOf(SlotLifeCycleManager slotLifeCycleManager) {
    return new SlotLifeCycleManager(
      slotLifeCycleManager.getRoomId(),
      slotLifeCycleManager.getSlotValidationInfo(),
      slotLifeCycleManager.getAuctionConfigInfo(),
      slotLifeCycleManager.getClassesConfig().entrySet().stream()
        .map(entry -> ClassConfig.of(entry.getKey(), entry.getValue().getReservedSlotsOfDays()))
        .collect(toMap(ClassConfig::getClassId, classConfig -> classConfig)),
      slotLifeCycleManager.getPreReservations().stream()
        .map(preReservation -> PreReservation.of(preReservation.getUserId(), preReservation.getReservationDate()))
        .collect(toList()));
  }

  @Override
  public void execute(long roomId, UnaryOperator<SlotLifeCycleManager> modifier, Supplier<? extends RuntimeException> notFoundExceptionSupplier) {
    withLock(lock, () -> {
      SlotLifeCycleManager slotLifeCycleManagerFound = find(roomId).orElseThrow(notFoundExceptionSupplier);
      store.put(roomId, modifier.apply(slotLifeCycleManagerFound));
    });
  }
}
