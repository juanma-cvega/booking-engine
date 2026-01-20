package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingUseCaseConfig {

    @Autowired private BookingManagerComponent bookingManagerComponent;

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Bean
    public CancelBookingUseCase cancelBookingUseCase() {
        return new CancelBookingUseCase(bookingManagerComponent, slotManagerComponent);
    }

    @Bean
    public CreateBookingUseCase createBookingUseCase() {
        return new CreateBookingUseCase(bookingManagerComponent);
    }

    @Bean
    public GetBookingsUseCase getBookingsUseCase() {
        return new GetBookingsUseCase(bookingManagerComponent);
    }
}
