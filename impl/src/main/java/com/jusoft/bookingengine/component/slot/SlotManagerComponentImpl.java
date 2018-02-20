package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotManagerComponentImpl implements SlotManagerComponent {

  private final SlotRepository slotRepository;
  private final SlotFactory slotFactory;
  private final SlotEventFactory slotEventFactory;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  @Override
  public SlotView create(CreateSlotCommand createSlotCommand) {
    Slot newSlot = slotFactory.createFrom(createSlotCommand);
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
  public void reserveSlot(ReserveSlotCommand command) {
    Slot slot = slotRepository.execute(command.getSlotId(), slotFound -> slotFound.reserve(clock));
    messagePublisher.publish(slotEventFactory.slotReservedEvent(slot, command));
  }

  @Override
  public void makeAvailable(long slotId) {
    Slot slotModified = slotRepository.execute(slotId, Slot::makeAvailable);
    messagePublisher.publish(slotEventFactory.slotMadeAvailableEvent(slotModified));
  }

  @Override
  public void reserveSlotForAuctionWinner(ReserveSlotCommand command) {
    Slot slot = slotRepository.execute(command.getSlotId(), Slot::reserveForAuctionWinner);
    messagePublisher.publish(slotEventFactory.slotReservedEvent(slot, command));
  }

  @Override
  public SlotView find(long slotId) {
    return slotFactory.createFrom(findSlotOrFail(slotId));
  }

  @Override
  public boolean isSlotOpen(long slotId) {
    return findSlotOrFail(slotId).isAvailable(clock);
  }

  private Slot findSlotOrFail(long slotId) {
    return slotRepository.find(slotId).orElseThrow(() -> new SlotNotFoundException(slotId));
  }
}
