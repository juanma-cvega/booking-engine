package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.usecase.booking.CancelBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.CreateBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.FindBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.GetBookingsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingControllerConfig {

    @Bean
    public BookingControllerRest bookingComponentRest(
            CreateBookingUseCase createBookingUseCase,
            CancelBookingUseCase cancelBookingUseCase,
            FindBookingUseCase findBookingUseCase,
            GetBookingsUseCase getBookingsUseCase) {
        return new BookingControllerRest(
                createBookingUseCase, cancelBookingUseCase, findBookingUseCase, getBookingsUseCase);
    }
}
