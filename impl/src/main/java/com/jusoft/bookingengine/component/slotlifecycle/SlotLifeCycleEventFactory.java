package com.jusoft.bookingengine.component.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.PersonReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotCanBeMadeAvailableEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresAuctionEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresPreReservationEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType;
import com.jusoft.bookingengine.publisher.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.CLASS;
import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;

class SlotLifeCycleEventFactory {

  <T extends NextSlotState> Event getEventFrom(T nextSlotState) {
    return EventType.valueOf(nextSlotState).createEventFrom(nextSlotState);
  }

  Event getEventFrom(long slotId, SlotUser slotUser) {
    return ReservationEventType.valueOf(slotUser.getUserType()).createEventFrom(slotId, slotUser);
  }

  private enum EventType {
    IN_AUCTION(InAuctionSlotStateFactory.INSTANCE),
    PRE_RESERVED(PreReservedSlotStateFactory.INSTANCE),
    AVAILABLE(AvailableSlotStateFactory.INSTANCE);

    private final SlotStateFactory factory;

    EventType(SlotStateFactory factory) {
      this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    <T extends NextSlotState> Event createEventFrom(T slotState) {
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
  private static class PreReservedSlotStateFactory implements SlotStateFactory<SlotRequiresPreReservationEvent, PreReservedState> {

    private static final PreReservedSlotStateFactory INSTANCE = new PreReservedSlotStateFactory();

    @Override
    public SlotRequiresPreReservationEvent getEventFrom(PreReservedState nextSlotState) {
      return SlotRequiresPreReservationEvent.of(nextSlotState.getSlotId(), nextSlotState.getUser());
    }

    @Override
    public Class<PreReservedState> getState() {
      return PreReservedState.class;
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  private static class AvailableSlotStateFactory implements SlotStateFactory<SlotCanBeMadeAvailableEvent, AvailableState> {

    private static final AvailableSlotStateFactory INSTANCE = new AvailableSlotStateFactory();

    @Override
    public SlotCanBeMadeAvailableEvent getEventFrom(AvailableState nextSlotState) {
      return SlotCanBeMadeAvailableEvent.of(nextSlotState.getSlotId());
    }

    @Override
    public Class<AvailableState> getState() {
      return AvailableState.class;
    }
  }

  private enum ReservationEventType {
    PERSON_RESERVATION(PersonReservationCreatedEventFactory.INSTANCE),
    CLASS_RESERVATION(ClassReservationCreatedEventFactory.INSTANCE);

    private final ReservationEventFactory factory;

    ReservationEventType(ReservationEventFactory factory) {
      this.factory = factory;
    }

    Event createEventFrom(long slotId, SlotUser slotUser) {
      return factory.createEventFrom(slotId, slotUser);
    }

    static ReservationEventType valueOf(UserType userType) {
      return Stream.of(values())
        .filter(eventType -> eventType.factory.getUserType().equals(userType))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("User type not supported"));
    }
  }

  private interface ReservationEventFactory {

    Event createEventFrom(long slotId, SlotUser slotUser);

    UserType getUserType();
  }

  private static class PersonReservationCreatedEventFactory implements ReservationEventFactory {

    private static final PersonReservationCreatedEventFactory INSTANCE = new PersonReservationCreatedEventFactory();

    @Override
    public Event createEventFrom(long slotId, SlotUser slotUser) {
      return PersonReservationCreatedEvent.of(slotId, slotUser.getId());
    }

    @Override
    public UserType getUserType() {
      return PERSON;
    }
  }

  private static class ClassReservationCreatedEventFactory implements ReservationEventFactory {

    private static final ClassReservationCreatedEventFactory INSTANCE = new ClassReservationCreatedEventFactory();

    @Override
    public Event createEventFrom(long slotId, SlotUser slotUser) {
      return ClassReservationCreatedEvent.of(slotId, slotUser.getId());
    }

    @Override
    public UserType getUserType() {
      return CLASS;
    }
  }
}
