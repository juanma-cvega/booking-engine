package com.jusoft.booking;

import com.jusoft.slot.SlotComponent;
import com.jusoft.slot.SlotResource;

import java.time.LocalDateTime;
import java.util.List;

class BookingService {

    private final SlotComponent slotComponent;
    private final SlotResourceFactory slotResourceFactory;
    private final BookingRepository bookingRepository;
    private final BookingFactory bookingFactory;

    BookingService(SlotComponent slotComponent, SlotResourceFactory slotResourceFactory, BookingRepository bookingRepository, BookingFactory bookingFactory) {
        this.slotComponent = slotComponent;
        this.slotResourceFactory = slotResourceFactory;
        this.bookingRepository = bookingRepository;
        this.bookingFactory = bookingFactory;
    }

    //TODO provide more fluent implementation of the validations. Different approach for the exceptions?
    /* The code explicitly doesn't check whether the slot is booked already. That constraint is set at the database level.
    * Validating it here would cause a performance penalty as it would require a call to the database and it won't guarantee
    * consistency in a multithreaded setup*/
    Booking book(CreateBookingRequest createBookingRequest) {
        SlotResource slotResource = slotComponent.find(createBookingRequest.getSlotId(), createBookingRequest.getRoomId());
        Slot slot = slotResourceFactory.createFrom(slotResource);

        validateSlotIsOpen(slot, createBookingRequest.getRequestTime());
        Booking newBooking = bookingFactory.create(createBookingRequest, slot);
        bookingRepository.save(newBooking);
        return newBooking;
    }

    //TODO provide a better way of handling validations
    void cancel(CancelBookingRequest cancelBookingRequest) {
        Booking booking = bookingRepository.find(cancelBookingRequest.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(cancelBookingRequest.getUserId(), cancelBookingRequest.getBookingId()));

        validateSlotIsOpen(booking.getSlot(), cancelBookingRequest.getRequestTime());
        validateUserOwnsBooking(cancelBookingRequest.getUserId(), booking);
        bookingRepository.delete(booking.getBookingId());
    }

    private void validateUserOwnsBooking(long userId, Booking booking) {
        if (Long.compare(userId, booking.getUserId()) != 0) {
            throw new WrongUserForSlotException(userId, booking.getUserId(), booking.getSlot().getSlotId());
        }
    }

    List<Booking> getFor(long userId) {
        return bookingRepository.getByUser(userId);
    }

    private void validateSlotIsOpen(Slot slot, LocalDateTime requestTime) {
        if (slot.getStartDate().isBefore(requestTime)) {
            throw new SlotAlreadyStartedException(slot.getSlotId(), slot.getRoomId(), slot.getStartDate());
        }
    }

    //FIXME normalize validation as it's done in two places
    public Booking find(Long userId, Long bookingId) {
        return bookingRepository.find(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(userId, bookingId));
    }
}
