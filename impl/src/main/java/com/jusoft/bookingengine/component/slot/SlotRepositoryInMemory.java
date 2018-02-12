package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static com.jusoft.bookingengine.util.LockingTemplate.withLock;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotRepositoryInMemory implements SlotRepository {

  private final Map<Long, Slot> store;
  private final Lock lock;
  private final Clock clock;

  @Override
  public Optional<Slot> find(long slotId) {
    Slot value = store.get(slotId);
    if (value != null) {
      return Optional.of(new Slot(value.getId(),
        value.getRoomId(),
        value.getCreationTime(),
        value.getOpenDate(),
        value.getState()));
    }
    return Optional.empty();
  }

  @Override
  public void save(Slot newSlot) {
    store.put(newSlot.getId(), newSlot);
  }

  @Override
  public Optional<Slot> getLastCreatedFor(long roomId) {
    return store.values().stream()
      .filter(slot -> slot.getRoomId() == roomId)
      .min(bySlotIdDesc());
  }

  private Comparator<? super Slot> bySlotIdDesc() {
    return Comparator.comparingLong(Slot::getId).reversed();
  }

  @Override
  public Optional<Slot> findSlotInUseOrToStartFor(long roomId) {
    return ofNullable(store.values().stream()
      .filter(bySlotInUse())
      .findFirst()
      .orElseGet(() -> store.values().stream()
        .filter(bySlotsToStart())
        .min(byStartDateAsc())
        .orElse(null)));
  }

  @Override
  public List<Slot> findOpenSlotsByRoom(long roomId) {
    return store.values().stream()
      .filter(slot -> Long.compare(slot.getRoomId(), roomId) == 0)
      .filter(slot -> slot.getOpenDate().getEndTime().isAfter(ZonedDateTime.now(clock)))
      .collect(toList());
  }

  @Override
  public Slot execute(long slotId, UnaryOperator<Slot> operation) {
    return withLock(lock, () -> {
      Slot slot = find(slotId).orElseThrow(() -> new SlotNotFoundException(slotId));
      Slot slotModified = operation.apply(slot);
      store.put(slotId, slotModified);
      return slotModified;
    });
  }

  private Comparator<Slot> byStartDateAsc() {
    return Comparator.comparing(slot -> slot.getOpenDate().getStartTime());
  }

  private Predicate<Slot> bySlotInUse() {
    return slot -> {
      ZonedDateTime now = ZonedDateTime.now(clock);
      return slot.getOpenDate().getStartTime().isBefore(now) && slot.getOpenDate().getEndTime().isAfter(now);
    };
  }

  private Predicate<Slot> bySlotsToStart() {
    return slot -> {
      ZonedDateTime now = ZonedDateTime.now(clock);
      return slot.getOpenDate().getStartTime().isAfter(now) || slot.getOpenDate().getStartTime().isEqual(now);
    };
  }
}
