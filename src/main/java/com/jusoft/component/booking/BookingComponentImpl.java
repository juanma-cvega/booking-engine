package com.jusoft.component.booking;

import com.jusoft.component.slot.Slot;
import com.jusoft.component.slot.SlotComponent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

class BookingComponentImpl implements BookingComponent {

    private final SlotComponent slotComponent;
    private final BookingRepository bookingRepository;
    private final BookingFactory bookingFactory;
    private final Supplier<LocalDateTime> instantSupplier;

    BookingComponentImpl(SlotComponent slotComponent,
                         BookingRepository bookingRepository,
                         BookingFactory bookingFactory,
                         Supplier<LocalDateTime> instantSupplier) {
        this.slotComponent = slotComponent;
        this.bookingRepository = bookingRepository;
        this.bookingFactory = bookingFactory;
        this.instantSupplier = instantSupplier;
    }

    //TODO provide more fluent implementation of the validations. Different approach for the exceptions?
    /* The code explicitly doesn't check whether the slot is booked already. That constraint is set at the database level.
    * Validating it here would cause a performance penalty as it would require a call to the database and it won't guarantee
    * consistency in a multithreaded setup*/
    public Booking book(CreateBookingCommand createBookingCommand) {
        Slot slot = slotComponent.find(createBookingCommand.getSlotId(), createBookingCommand.getRoomId());

        validateSlotIsOpen(slot, instantSupplier.get());
        Booking newBooking = bookingFactory.create(createBookingCommand, slot);
        bookingRepository.save(newBooking);
        return newBooking;
    }

    //TODO provide a better way of handling validations
    public void cancel(CancelBookingCommand cancelBookingCommand) {
        Booking booking = bookingRepository.find(cancelBookingCommand.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException(cancelBookingCommand.getUserId(), cancelBookingCommand.getBookingId()));

        validateSlotIsOpen(booking.getSlot(), instantSupplier.get());
        validateUserOwnsBooking(cancelBookingCommand.getUserId(), booking);
        bookingRepository.delete(booking.getBookingId());
    }

    private void validateUserOwnsBooking(long userId, Booking booking) {
        if (Long.compare(userId, booking.getUserId()) != 0) {
            throw new WrongBookingUserException(userId, booking.getUserId(), booking.getBookingId());
        }
    }

    public List<Booking> getFor(long userId) {
        return bookingRepository.getByUser(userId);
    }

    private void validateSlotIsOpen(Slot slot, LocalDateTime requestTime) {
        if (slot.getStartDate().isBefore(requestTime)) {
            throw new SlotAlreadyStartedException(slot.getSlotId(), slot.getRoomId(), slot.getStartDate());
        }
    }

    //FIXME normalize validation as it's done in two places
    public Booking find(long userId, long bookingId) {
        return bookingRepository.find(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(userId, bookingId));
    }

}
