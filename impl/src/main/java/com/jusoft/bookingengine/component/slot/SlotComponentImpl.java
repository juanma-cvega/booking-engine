package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotComponentImpl implements SlotComponent {

  private final SlotRepository slotRepository;
  private final SlotFactory slotFactory;
  private final SlotEventFactory slotEventFactory;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  @Override
  public Slot create(CreateSlotCommand createSlotCommand, Clock clock) {
    Slot newSlot = slotFactory.createFrom(createSlotCommand, clock);
    slotRepository.save(newSlot);
    messagePublisher.publish(slotEventFactory.slotCreatedEvent(newSlot));
    return newSlot;
  }

  @Override
  public List<Slot> findOpenSlotsFor(long roomId) {
    return slotRepository.getByRoom(roomId);
  }

  @Override
  public Optional<Slot> findLastCreatedFor(long roomId) {
    return slotRepository.getLastCreatedFor(roomId);
  }

  @Override
  public Optional<Slot> findSlotInUseOrToStartFor(long roomId) {
    return slotRepository.findSlotInUseOrToStartFor(roomId);
  }

  @Override
  public Slot find(long slotId, long roomId) {
    return slotRepository.find(slotId, roomId).orElseThrow(() -> new SlotNotFoundException(slotId, roomId));
  }

  @Override
  public boolean isSlotOpen(long slotId, long roomId) {
    return find(slotId, roomId).isOpen(ZonedDateTime.now(clock));
  }
}
