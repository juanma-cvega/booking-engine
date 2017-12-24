package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.publisher.MessagePublisher;
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
  public SlotView create(CreateSlotCommand createSlotCommand) {
    Slot newSlot = slotFactory.createFrom(createSlotCommand, clock);
    slotRepository.save(newSlot);
    messagePublisher.publish(slotEventFactory.slotCreatedEvent(newSlot));
    return slotFactory.createFrom(newSlot);
  }

  @Override
  public List<SlotView> findOpenSlotsFor(long roomId) {
    return slotFactory.createFrom(slotRepository.findOpenSlotsByRoom(roomId));
  }

  @Override
  public Optional<SlotView> findLastCreatedFor(long roomId) {
    Optional<Slot> lastCreated = slotRepository.getLastCreatedFor(roomId);
    return lastCreated.map(slotFactory::createFrom);
  }

  @Override
  public Optional<SlotView> findSlotInUseOrToStartFor(long roomId) {
    return slotRepository.findSlotInUseOrToStartFor(roomId).map(slotFactory::createFrom);
  }

  @Override
  public int findNumberOfSlotsOpenFor(long roomId) {
    return findOpenSlotsFor(roomId).size();
  }

  @Override
  public SlotView find(long slotId, long roomId) {
    return slotFactory.createFrom(findSlotOrFail(slotId, roomId));
  }

  @Override
  public boolean isSlotOpen(long slotId, long roomId) {
    return findSlotOrFail(slotId, roomId).isOpen(ZonedDateTime.now(clock));
  }

  private Slot findSlotOrFail(long slotId, long roomId) {
    return slotRepository.find(slotId, roomId).orElseThrow(() -> new SlotNotFoundException(slotId, roomId));
  }
}
