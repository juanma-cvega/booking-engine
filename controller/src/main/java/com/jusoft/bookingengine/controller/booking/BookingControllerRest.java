package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.CreateBookingCommand;
import com.jusoft.bookingengine.controller.booking.api.BookingResource;
import com.jusoft.bookingengine.controller.booking.api.CreateBookingRequest;
import com.jusoft.bookingengine.usecase.booking.CancelBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.CreateBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.FindBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.GetBookingsUseCase;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/bookings")
class BookingControllerRest {

    private final CreateBookingUseCase createBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final FindBookingUseCase findBookingUseCase;
    private final GetBookingsUseCase getBookingsUseCase;

    BookingControllerRest(
            CreateBookingUseCase createBookingUseCase,
            CancelBookingUseCase cancelBookingUseCase,
            FindBookingUseCase findBookingUseCase,
            GetBookingsUseCase getBookingsUseCase) {
        this.createBookingUseCase = createBookingUseCase;
        this.cancelBookingUseCase = cancelBookingUseCase;
        this.findBookingUseCase = findBookingUseCase;
        this.getBookingsUseCase = getBookingsUseCase;
    }

    @PostMapping(
            value = "/room/{roomId}/slot/{slotId}/booking",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResource book(
            @PathVariable long roomId,
            @PathVariable long slotId,
            @Valid @RequestBody CreateBookingRequest command) {
        log.info(
                "Create booking request received: roomId={}, slotId={}, userId={}",
                roomId,
                slotId,
                command.getUserId());
        BookingView booking =
                createBookingUseCase.book(new CreateBookingCommand(command.getUserId(), slotId));
        BookingResource bookingResource = BookingResource.from(booking);
        log.info("Create booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @DeleteMapping(value = "/user/{userId}/booking/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable long userId, @PathVariable long bookingId) {
        log.info("Cancel booking request received: userId={}, bookingId={}", userId, bookingId);
        cancelBookingUseCase.cancel(userId, bookingId);
        log.info("Cancel booking request finished");
    }

    @GetMapping(value = "/user/{userId}/booking/{bookingId}", produces = "application/json")
    public BookingResource find(@PathVariable long userId, @PathVariable long bookingId) {
        log.info("Find booking request received: userId={}, bookingId={}", userId, bookingId);
        BookingView booking = findBookingUseCase.find(bookingId);
        BookingResource bookingResource = BookingResource.from(booking);
        log.info("Find booking request finished: booking={}", bookingResource);
        return bookingResource;
    }

    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public BookingResources getFor(@PathVariable long userId) {
        log.info("Create booking request received: userId={}", userId);
        List<BookingView> bookings = getBookingsUseCase.getBookingsFor(userId);
        BookingResources bookingResources =
                new BookingResources(bookings.stream().map(BookingResource::from).toList());
        log.info(
                "Create booking request finished: userId={}, bookings={}",
                userId,
                bookingResources.getBookings().size());
        return bookingResources;
    }
}
