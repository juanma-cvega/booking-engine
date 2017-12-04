package com.jusoft.bookingengine.component.slot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotRepositoryInMemory implements SlotRepository {

  private final Map<Long, Slot> store;
  private final Clock clock;

  @Override
  public List<Slot> getByRoom(long roomId) {
    return store.values().stream().filter(slot -> Long.compare(slot.getRoomId(), roomId) == 0).collect(toList());
  }

  @Override
  public Optional<Slot> find(long slotId, long roomId) {
    return ofNullable(store.get(slotId)).filter(slotFound -> Long.compare(slotFound.getRoomId(), roomId) == 0);
  }

  @Override
  public void save(Slot newSlot) {
    store.put(newSlot.getId(), newSlot);
  }

  @Override
  public Optional<Slot> getLastCreatedFor(long roomId) {
    return store.values().stream()
      .filter(slot -> slot.getRoomId() == roomId)
      .sorted(bySlotIdDesc())
      .findFirst();
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
        .sorted(byStartDateAsc())
        .findFirst()
        .orElse(null)));
  }

  private Comparator<Slot> byStartDateAsc() {
    return Comparator.comparing(Slot::getStartDate);
  }

  private Predicate<Slot> bySlotInUse() {
    return slot -> {
      ZonedDateTime now = ZonedDateTime.now(clock);
      return slot.getStartDate().isBefore(now) && slot.getEndDate().isAfter(now);
    };
  }

  private Predicate<Slot> bySlotsToStart() {
    return slot -> {
      ZonedDateTime now = ZonedDateTime.now(clock);
      return slot.getStartDate().isAfter(now) || slot.getStartDate().isEqual(now);
    };
  }
}