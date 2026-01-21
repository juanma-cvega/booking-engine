package com.jusoft.bookingengine.component.slotlifecycle;

import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.CLASS;
import static com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType.PERSON;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.PersonReservationCreatedEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotCanBeMadeAvailableEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresAuctionEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotRequiresPreReservationEvent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser.UserType;
import com.jusoft.bookingengine.publisher.Event;
import java.util.stream.Stream;

class SlotLifeCycleEventFactory {

    <T extends NextSlotState> Event getEventFrom(T nextSlotState) {
        return switch (nextSlotState) {
            case InAuctionState(var slotId, var auctionConfigInfo) ->
                    new SlotRequiresAuctionEvent(slotId, auctionConfigInfo);
            case PreReservedState(var slotId, var user) ->
                    new SlotRequiresPreReservationEvent(slotId, user);
            case AvailableState(var slotId) -> new SlotCanBeMadeAvailableEvent(slotId);
        };
    }

    Event getEventFrom(long slotId, SlotUser slotUser) {
        return ReservationEventType.valueOf(slotUser.getUserType())
                .createEventFrom(slotId, slotUser);
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

        private static final PersonReservationCreatedEventFactory INSTANCE =
                new PersonReservationCreatedEventFactory();

        @Override
        public Event createEventFrom(long slotId, SlotUser slotUser) {
            return new PersonReservationCreatedEvent(slotId, slotUser.getId());
        }

        @Override
        public UserType getUserType() {
            return PERSON;
        }
    }

    private static class ClassReservationCreatedEventFactory implements ReservationEventFactory {

        private static final ClassReservationCreatedEventFactory INSTANCE =
                new ClassReservationCreatedEventFactory();

        @Override
        public Event createEventFrom(long slotId, SlotUser slotUser) {
            return new ClassReservationCreatedEvent(slotId, slotUser.getId());
        }

        @Override
        public UserType getUserType() {
            return CLASS;
        }
    }
}
