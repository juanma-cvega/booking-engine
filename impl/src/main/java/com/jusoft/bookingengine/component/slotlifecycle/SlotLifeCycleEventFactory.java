package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotNeededForClassEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotPreReservedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotReadyEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresAuctionEvent;
import com.jusoft.bookingengine.publisher.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

class SlotLifeCycleEventFactory {

  public <T extends NextSlotState> Event getEventFrom(T nextSlotState) {
    return EventType.valueOf(nextSlotState).createEventFrom(nextSlotState);
  }

  private enum EventType {
    IN_AUCTION(InAuctionSlotStateFactory.INSTANCE),
    RESERVED_FOR_CLASS(ReservedForClassSlotStateFactory.INSTANCE),
    PRE_RESERVED(PreReservedSlotStateFactory.INSTANCE),
    AVAILABLE(AvailableSlotStateFactory.INSTANCE);

    private final SlotStateFactory factory;

    EventType(SlotStateFactory factory) {
      this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    public <T extends NextSlotState> Event createEventFrom(T slotState) {
      return factory.getEventFrom(slotState);
    }

    static <T extends NextSlotState> EventType valueOf(T nextSlotState) {
      return Stream.of(values())
        .filter(eventType -> eventType.factory.getState().equals(nextSlotState.getClass()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("State not supported"));
    }
  }

  private interface SlotStateFactory<T extends Event, U extends NextSlotState> {

    T getEventFrom(U nextSlotState);

    Class<U> getState();
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class InAuctionSlotStateFactory implements SlotStateFactory<SlotRequiresAuctionEvent, InAuctionState> {


    private static final InAuctionSlotStateFactory INSTANCE = new InAuctionSlotStateFactory();

    @Override
    public SlotRequiresAuctionEvent getEventFrom(InAuctionState nextSlotState) {
      return SlotRequiresAuctionEvent.of(nextSlotState.getSlotId(), nextSlotState.getAuctionConfigInfo());
    }

    @Override
    public Class<InAuctionState> getState() {
      return InAuctionState.class;
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class ReservedForClassSlotStateFactory implements SlotStateFactory<SlotNeededForClassEvent, ReservedForClassState> {

    private static final ReservedForClassSlotStateFactory INSTANCE = new ReservedForClassSlotStateFactory();

    @Override
    public SlotNeededForClassEvent getEventFrom(ReservedForClassState nextSlotState) {
      return SlotNeededForClassEvent.of(nextSlotState.getSlotId(), nextSlotState.getClassId());
    }

    @Override
    public Class<ReservedForClassState> getState() {
      return ReservedForClassState.class;
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class PreReservedSlotStateFactory implements SlotStateFactory<SlotPreReservedEvent, PreReservedState> {

    private static final PreReservedSlotStateFactory INSTANCE = new PreReservedSlotStateFactory();

    @Override
    public SlotPreReservedEvent getEventFrom(PreReservedState nextSlotState) {
      return SlotPreReservedEvent.of(nextSlotState.getUserId(), nextSlotState.getSlotId());
    }

    @Override
    public Class<PreReservedState> getState() {
      return PreReservedState.class;
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class AvailableSlotStateFactory implements SlotStateFactory<SlotReadyEvent, AvailableState> {

    private static final AvailableSlotStateFactory INSTANCE = new AvailableSlotStateFactory();

    @Override
    public SlotReadyEvent getEventFrom(AvailableState nextSlotState) {
      return SlotReadyEvent.of(nextSlotState.getSlotId());
    }

    @Override
    public Class<AvailableState> getState() {
      return AvailableState.class;
    }
  }

}
